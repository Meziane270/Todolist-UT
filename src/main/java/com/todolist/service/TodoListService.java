package com.todolist.service;

import com.todolist.model.Item;
import com.todolist.model.TodoList;

import java.util.List;

public interface TodoListService {

    List<TodoList> getTodoLists();

    TodoList getTodoListById(long id);

    TodoList createTodoList(long userId, TodoList todoList);

    TodoList addItem(long id, Item item);

    void updateTodoList(long id, TodoList todoList);

    void deleteTodoList(long id);
}
