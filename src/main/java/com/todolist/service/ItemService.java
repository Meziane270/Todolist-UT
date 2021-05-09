package com.todolist.service;

import com.todolist.model.Item;
import com.todolist.model.TodoList;

import java.util.List;

public interface ItemService {
    List<Item> getItems();

    TodoList getItemById(long id);

    TodoList createItem(long userId, Item item);

    void updateItem(long id, Item item);

    void deleteItem(long id);
}
