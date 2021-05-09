package com.todolist.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private long id;
    @Singular

    @OneToOne
    private final TodoList todoList = new TodoList(this);

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthDate;

    public boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return Period.between(birthDate, LocalDate.now()).getYears() >= 13 && !firstname.isBlank() && !lastname.isBlank() && pat.matcher(email).matches() && password.length() >= 8 && password.length() <= 30;
    }
}
