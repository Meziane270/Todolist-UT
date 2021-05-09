package com.todolist.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Singular

    @OneToOne
    private final TodoList todoList = new TodoList(this);

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String firstname;

    @NonNull
    @Column(nullable = false)
    private String lastname;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(nullable = false)
    private LocalDate birthDate;

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private long id;

    public boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return Period.between(birthDate, LocalDate.now()).getYears() >= 13 && !firstname.isBlank() && !lastname.isBlank() && pat.matcher(email).matches() && password.length() >= 8 && password.length() <= 30;
    }
}
