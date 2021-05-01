package com.todolist.model;

import com.todolist.service.EmailSenderService;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TodoList {
    @Singular
    private final ArrayList<Item> items = new ArrayList<>();
    private EmailSenderService emailSenderService = new EmailSenderService();
    @NonNull
    private User user;

    public void addItem(Item item) {
        if (getItemsCount() < 10 &&
                item.isValid() &&
                !containsItemWithName(item.getName()) &&
                lastInsertedItemInLastThirtyMinutes(item.getCreationDate())) {
            items.add(item);
            if (getItemsCount() == 8) {
                emailSenderService.sendMail(user.getEmail());
            }
        }
    }
    public int getItemsCount(){
        return items.size();
    }

    public boolean containsItemWithName(String name){
       return items.stream().anyMatch(it -> it.getName().equals(name));
    }

    public boolean lastInsertedItemInLastThirtyMinutes(LocalDateTime time){
        Item item = items.stream().min(Comparator.comparing(Item::getCreationDate)).orElse(null);
        if(item == null) return true;
        return ChronoUnit.MINUTES.between(item.getCreationDate(), time) > 30;
    }
}
