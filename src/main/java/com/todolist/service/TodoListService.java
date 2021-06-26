package com.todolist.service;

import com.todolist.model.Item;
import com.todolist.model.TodoList;

import java.io.InvalidObjectException;
import java.util.List;

public interface TodoListService {

    List<TodoList> getTodoLists();

    TodoList getTodoListById(long id);

    TodoList addItem(long id, Item item) throws InvalidObjectException;

    void deleteTodoList(long id);
    TodoList addItem(long id, Item item);
}
