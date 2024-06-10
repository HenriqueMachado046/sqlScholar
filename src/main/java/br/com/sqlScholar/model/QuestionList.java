package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table
public class QuestionList {
    @Id
    @GeneratedValue(generator = "UUID")
    @PrimaryKeyJoinColumn
    private UUID id;
    @ManyToMany(mappedBy = "questionLists")
    private List<Question> questions;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column
    private String title;
    @Column
    private boolean isPrivate;
    @ManyToOne
    private Teacher teacher;

}