package com.todolist.model;

import com.todolist.service.EmailSenderService;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class TodoList {
    @Singular
    @OneToMany
    private final ArrayList<Item> items = new ArrayList<>();
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private long id;
    @NonNull
    @OneToOne
    private User user;

    @Transient
    private EmailSenderService emailSenderService = new EmailSenderService();

    public void addItem(Item item) {
        if (getItemsCount() < 10 &&
                item.isValid() &&
                !containsItemWithName(item.getName()) &&
                !lastInsertedItemInLastThirtyMinutes()) {
            items.add(item);
            if (getItemsCount() == 8) {
                emailSenderService.sendMail(user.getEmail());
            }
        }
    }

    public int getItemsCount() {
        return items.size();
    }

    public boolean containsItemWithName(String name) {
        return items.stream().anyMatch(it -> it.getName().equals(name));
    }

    public boolean lastInsertedItemInLastThirtyMinutes() {
        Item item = items.stream().min(Comparator.comparing(Item::getCreationDate)).orElse(null);
        if (item == null) return false;
        long now = new Timestamp(new Date().getTime()).getTime() / 1000;
        long lastCreatedDate = item.getCreationDate().getTime() / 1000;
        return now - lastCreatedDate < 30;
    }
}
