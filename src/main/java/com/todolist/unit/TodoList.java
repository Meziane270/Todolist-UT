package com.todolist.unit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todolist.service.EmailSenderService;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@JsonFormat(pattern = "dd/MM/YYYY")
@Table(name = "T_TodoList")
public class TodoList {
    @Singular
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    private final List<Item> items = new ArrayList<>();

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Transient
    private EmailSenderService emailSenderService = new EmailSenderService();

    public void addItem(Item item) {
        if (getItemsCount() < 10 &&
                item.isValid() &&
                !containsItemWithName(item.getName()) &&
                !lastInsertedItemInLastThirtyMinutes()) {
            items.add(item);
            item.setTodoList(this);
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
        long now = new Timestamp(new Date().getTime()).getTime() / 1800000;
        long lastCreatedDate = item.getCreationDate().getTime() / 1800000;
        return now - lastCreatedDate < 30;
    }
}
