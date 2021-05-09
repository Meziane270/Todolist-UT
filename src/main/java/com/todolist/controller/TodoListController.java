package com.todolist.controller;

import com.todolist.model.Item;
import com.todolist.model.TodoList;
import com.todolist.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todo-list")
public class TodoListController {

    TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public ResponseEntity<List<TodoList>> getAllTodoLists() {
        List<TodoList> todoLists = todoListService.getTodoLists();
        return new ResponseEntity<>(todoLists, HttpStatus.OK);
    }

    @GetMapping({"/{todoListId}"})
    public ResponseEntity<TodoList> getTodoListById(@PathVariable Long todoListId) {
        TodoList todoList = todoListService.getTodoListById(todoListId);
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PostMapping({"/{todoListId}/item"})
    public ResponseEntity<TodoList> addItem(@PathVariable long todoListId, @RequestBody Item item) {
        TodoList todoList = todoListService.addItem(todoListId, item);
        return new ResponseEntity<>(todoList, HttpStatus.CREATED);
    }

    @DeleteMapping({"/{todoListId}"})
    public ResponseEntity<TodoList> deleteTodo(@PathVariable("todoListId") Long todoListId) {
        todoListService.deleteTodoList(todoListId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
