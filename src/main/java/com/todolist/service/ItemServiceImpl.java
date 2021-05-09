package com.todolist.service;

import com.todolist.model.Item;
import com.todolist.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public void updateItem(long id, Item item) {
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }
}
