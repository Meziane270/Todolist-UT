package com.todolist.model;

import com.todolist.service.EmailSenderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TodoListTest {
    private TodoList emptyTodoList;
    private TodoList fullTodoList;
    private TodoList oneItemTodoList;
    private TodoList sevenItemTodoList;
    private TodoList eightItemTodoList;
    private EmailSenderService emailSenderService;
    private Item validItem;
    private Item invalidItem;


    @Before
    public void setUp() {
        String email = "email@email.com";
        String uniqueItemName = "not-unique";
        User user = new User();
        user.setEmail(email);

        emailSenderService = Mockito.mock(EmailSenderService.class);

        validItem = Mockito.spy(new Item(uniqueItemName,"aaa"));
        Mockito.when(validItem.isValid()).thenReturn(true);

        invalidItem = Mockito.spy(new Item(uniqueItemName,"aaa"));
        Mockito.when(invalidItem.isValid()).thenReturn(false);

        emptyTodoList = new TodoList();
        emptyTodoList.setUser(user);

        oneItemTodoList = Mockito.spy(new TodoList());
        oneItemTodoList.addItem(new Item(uniqueItemName,"aaa"));
        Mockito.when(oneItemTodoList.getItemsCount()).thenReturn(1);

        fullTodoList = Mockito.spy(new TodoList());
        Mockito.when(fullTodoList.getItemsCount()).thenReturn(10);
        fullTodoList.setUser(user);

        sevenItemTodoList = Mockito.spy(new TodoList(user));
        Mockito.when(sevenItemTodoList.getItemsCount()).thenReturn(7).thenReturn(8);
        sevenItemTodoList.setEmailSenderService(emailSenderService);

        eightItemTodoList = Mockito.spy(new TodoList(user));
        Mockito.when(eightItemTodoList.getItemsCount()).thenReturn(8);
        eightItemTodoList.setEmailSenderService(emailSenderService);
    }

    @Test
    public void insertInEmptyTodoListValidItem(){
        emptyTodoList.addItem(validItem);
        assertTrue(emptyTodoList.getItems().contains(validItem));
    }

    @Test
    public void insertInEmptyTodoListInvalidItem(){
        emptyTodoList.addItem(invalidItem);
        assertFalse(emptyTodoList.getItems().contains(invalidItem));
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
        oneItemTodoList.addItem(validItem);
        assertFalse(oneItemTodoList.getItems().contains(validItem));
    }

    @Test
    public void addBeforeCoolDown(){
        emptyTodoList.addItem(new Item("1","a"));
        Item second = new Item("2","a");
        emptyTodoList.addItem(second);
        assertFalse(emptyTodoList.getItems().contains(second));
    }

    @Test
    public void sendOneEmailAtEightItems(){
        sevenItemTodoList.addItem(validItem);
        verify(emailSenderService, times(1)).sendMail(anyString());
    }

    @Test
    public void sendNoMailOnInvalidInsert(){
        eightItemTodoList.addItem(invalidItem);
        verify(emailSenderService, never()).sendMail(anyString());
    }
}