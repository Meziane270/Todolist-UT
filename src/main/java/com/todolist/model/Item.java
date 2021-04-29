package com.todolist.model;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime creationDate = LocalDateTime.now();

    public boolean isValid() {
        return true;
    }
}
