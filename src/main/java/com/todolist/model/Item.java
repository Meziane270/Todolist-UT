package com.todolist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@JsonFormat(pattern = "dd/MM/YYYY")
@Table(name = "T_Item")
public class Item {
    @NonNull
    @Column(unique = true, nullable = false)
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TodoList todoList;

    @NonNull
    @Column(nullable = false)
    private String content;

    @Column(updatable = false, nullable = false)
    private Timestamp creationDate;

    @Id
    @GeneratedValue
    private long id;

    public Item() {
        content = "";
        name = "";
    }

    public boolean isValid() {
        return !content.isBlank() && !name.isBlank() && content.length() <= 1000;
    }

    public int getContentSize() {
        return content.length();
    }

    @PrePersist
    private void onCreate() {
        this.setCreationDate(new Timestamp(new Date().getTime()));
    }
}
