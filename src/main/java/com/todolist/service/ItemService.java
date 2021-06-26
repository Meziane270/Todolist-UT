package com.todolist.service;

import com.todolist.model.Item;

import java.io.InvalidObjectException;
import java.util.List;

public interface ItemService {
    List<Item> getItems();

    Item getItemById(long id);

    void updateItem(long id, Item item) throws InvalidObjectException;

    void deleteItem(long id);
}
