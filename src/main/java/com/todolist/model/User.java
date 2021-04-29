package com.todolist.model;

import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

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
    private LocalDate birthDate;

    public boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return Period.between(birthDate, LocalDate.now()).getYears() >= 13 && !firstname.isBlank() && !lastname.isBlank() && pat.matcher(email).matches() && password.length() >= 8 && password.length() <= 30;
    }
}
