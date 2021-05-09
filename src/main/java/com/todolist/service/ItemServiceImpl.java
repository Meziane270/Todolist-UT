package com.todolist.service;

import com.todolist.model.Item;
import com.todolist.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
    public Item getItemById(long id) throws NoSuchElementException {
        return itemRepository.findById(id).get();
    }

    @Override
    public void updateItem(long id, Item item) throws NoSuchElementException {
        Item itemFromDb = itemRepository.findById(id).get();
        itemFromDb.setContent(item.getContent());
        itemFromDb.setName(item.getName());
        itemRepository.save(item);

    }

    @Override
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }
}
