package com.todolist.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Item {
    @NonNull
    private String name;
    @NonNull
    private String content;
    private LocalDate creationDate = LocalDate.now();


}
