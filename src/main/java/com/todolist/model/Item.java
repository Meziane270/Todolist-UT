package com.todolist.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "T_Item")
public class Item {
    @NonNull
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    @NonNull
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp creationDate = new Timestamp(new Date().getTime());

    @Id
    @GeneratedValue
    private long id;

    public boolean isValid() {
        return true;
    }

    public int getContentSize() {
        return content.length();
    }
}
