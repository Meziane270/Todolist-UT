package com.todolist.model;

import com.todolist.service.EmailSenderService;
import lombok.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoList {
    @Singular
    private final ArrayList<Item> items = new ArrayList<>();
    private User user;

    public void addItem(Item item) {
        Item lastItem = items.stream().min(Comparator.comparing(Item::getCreationDate)).orElse(null);
        if (items.size() < 10 &&
                item.isValid() &&
                items.stream().noneMatch(it -> it.getName().equals(item.getName())) &&
                (lastItem == null ||
                        ChronoUnit.MINUTES.between(lastItem.getCreationDate(), item.getCreationDate()) > 30)) {
            items.add(item);
        }
        if (items.size() == 8) {
            EmailSenderService emailSenderService = new EmailSenderService();
            emailSenderService.sendMail(user.getEmail());
        }
    }
}
