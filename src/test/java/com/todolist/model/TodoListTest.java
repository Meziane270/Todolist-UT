package com.todolist.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TodoListTest {
    private String uniqueItemName;
    private TodoList emptyTodoList;
    private TodoList fullTodoList;
    private TodoList oneItemTodoList;

    @Before
    public void setUp() {
        uniqueItemName = "unique";
        User user = new User();
        user.setEmail("email@email.com");
        emptyTodoList = new TodoList();
        emptyTodoList.setUser(user);
        oneItemTodoList = new TodoList();
        oneItemTodoList.setUser(user);
        oneItemTodoList.addItem(new Item(uniqueItemName,"aaaa", LocalDateTime.of(2020,1,1,0,0,0)));
        fullTodoList = new TodoList();
        fullTodoList.setUser(user);
        for(int i=0;i<10;i++){
            fullTodoList.addItem(new Item(String.valueOf(i),"bbbb", LocalDateTime.of(2020,i+1,1,0,0,0)));
        }
    }

    @Test
    public void numberOfItemIsFine(){
        Item item = new Item("1","a");
        emptyTodoList.addItem(item);
        assertTrue(emptyTodoList.getItems().contains(item));
    }

    @Test
    public void numberOfItemIsNotFine(){
        Item item = new Item("11","a");
        fullTodoList.addItem(item);
        assertFalse(fullTodoList.getItems().contains(item));
        assertTrue(fullTodoList.getItems().size()<=10);
    }

    @Test
    public void insertAlreadyUsedNameItem(){
        Item item = new Item(uniqueItemName,"aaa");
        oneItemTodoList.addItem(item);
        assertFalse(oneItemTodoList.getItems().contains(item));
    }

    @Test
    public void addBeforeCoolDown(){
        emptyTodoList.addItem(new Item("1","a"));
        emptyTodoList.addItem(new Item("2","a"));
        assertFalse(emptyTodoList.getItems().contains( new Item("2","a")));
    }
}