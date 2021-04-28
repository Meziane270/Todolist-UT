package com.todolist.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Singular
    private final TodoList todoList = new TodoList();
    private String email;
    private String lastname;
    private String firstname;
    private String password;
    private Date birthDate;

    public boolean isValid() {
        return false;
    }
}
