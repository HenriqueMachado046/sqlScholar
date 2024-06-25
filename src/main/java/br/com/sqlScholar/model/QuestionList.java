package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "questionlist")
public class QuestionList {
    @Id
    @GeneratedValue(generator = "UUID")
    @PrimaryKeyJoinColumn
    private UUID id;
    @ManyToMany(mappedBy = "questionLists")
    private List<Question> questions = new ArrayList<>();
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