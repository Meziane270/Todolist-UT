package com.todolist.model;

import java.util.Date;

public class User {
    private String email;
    private String lastname;
    private String password;
    private Date birthDate;
    private TodoList todoList;

    public boolean isValid(){
        return false;
    }
}
