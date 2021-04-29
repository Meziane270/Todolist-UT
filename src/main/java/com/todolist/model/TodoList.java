package com.todolist.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoList {
    @Singular
    private final ArrayList<Item> items = new ArrayList<>();
    public String email;


    public void addItem(Item itemToAdd){
        if(this.items.size() < 10){
            return;
        }
        this.items.add(itemToAdd);
        if(this.items.size() == 8 ){
            // sendEmail()
        }
    }

}
