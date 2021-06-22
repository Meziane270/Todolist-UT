package com.todolist.service;

import com.todolist.model.Item;
import com.todolist.model.TodoList;
import com.todolist.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService {
    TodoListRepository toDoListRepository;

    public TodoListServiceImpl(TodoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public List<TodoList> getTodoLists() {
        return toDoListRepository.findAll();
    }

    @Override
    public TodoList getTodoListById(long id) {
        return toDoListRepository.findById(id).get();
    }

    @Override
    public TodoList addItem(long id, Item item) {
        TodoList todoList = toDoListRepository.findById(id).get();
        System.out.println(todoList.getItemsCount());
        todoList.addItem(item);
        System.out.println(todoList.getItemsCount());
        toDoListRepository.save(todoList);
        return todoList;
    }
}
