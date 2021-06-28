package com.todolist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NonNull
    @Column(updatable = false, nullable = false)
    private Timestamp creationDate = new Timestamp(new Date().getTime());

    @Id
    @Singular
    @GeneratedValue
    private long id;

    public Item() {
        content = "";
        name = "";
    }

    public Item(@NonNull String name, @NonNull String content, @NonNull Timestamp creationDate) {
        this.name = name;
        this.content = content;
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public boolean isValid() {
        return !content.isBlank() && !name.isBlank() && content.length() <= 1000;
    }

    public int getContentSize() {
        return content.length();
    }
}
