package com.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoList {
    private User user;
    private ArrayList<Item> items;

    public void addItem(Item item) {

    }
}
