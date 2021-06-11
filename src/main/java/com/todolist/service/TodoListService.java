package com.todolist.service;

import com.todolist.unit.Item;
import com.todolist.unit.TodoList;

import java.util.List;

public interface TodoListService {

    List<TodoList> getTodoLists();

    TodoList getTodoListById(long id);

    TodoList addItem(long id, Item item);

    void deleteTodoList(long id);
}
