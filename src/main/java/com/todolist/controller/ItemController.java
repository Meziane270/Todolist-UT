package com.todolist.controller;

import com.todolist.model.Item;
import com.todolist.model.User;
import com.todolist.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.List;

@RestController
@RequestMapping(value = "/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping({"/{itemId}"})
    public ResponseEntity<Item> getItem(@PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
    }

    @PutMapping({"/{itemId}"})
    public ResponseEntity<Item> updateItem(@PathVariable("itemId") Long itemId, @RequestBody Item item) throws InvalidObjectException {
        itemService.updateItem(itemId, item);
        return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
    }

    @DeleteMapping({"/{itemId}"})
    public ResponseEntity<User> deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
