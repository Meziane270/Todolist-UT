package com.todolist.service;

import com.todolist.model.TodoList;

import java.util.List;

public interface TodoListService {

    List<TodoList> getTodoLists();

    TodoList getTodoListById(long id);

    TodoList createTodoList(long userId, TodoList todoList);

    void updateTodoList(long id, TodoList todoList);

    void deleteTodoList(long id);
}
