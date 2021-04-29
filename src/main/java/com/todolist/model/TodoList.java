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

    public void addItem(Item item) {

    }
}
