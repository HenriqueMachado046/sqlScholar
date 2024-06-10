package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table
public class Question {

    @Id
    @GeneratedValue(generator = "UUID")
    @PrimaryKeyJoinColumn
    private UUID id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private boolean isShared;
    @Column
    private String answerSheet;
    @OneToMany
    private List<TestCase> testCases;
    @Column
    private String tag;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "questionlist_id")
    )
    private List<QuestionList> questionLists;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;


}