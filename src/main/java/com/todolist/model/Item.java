package com.todolist.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
    private String name;
    private String content;
    @Singular
    private final LocalDate creationDate = LocalDate.now();


}
