package com.todolist.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String content;

    @Column(updatable = false, nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    public boolean isValid() {
        return true;
    }

    public int getContentSize() {
        return content.length();
    }

}
