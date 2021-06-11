package com.todolist.service;

import com.todolist.unit.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems();

    Item getItemById(long id);

    void updateItem(long id, Item item);

    void deleteItem(long id);
}
