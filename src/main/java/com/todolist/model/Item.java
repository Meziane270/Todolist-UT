package com.todolist.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Item {

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp creationDate = new Timestamp(new Date().getTime());

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private long id;

    public boolean isValid() {
        return true;
    }

    public int getContentSize() {
        return content.length();
    }
}
