package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String sql;
    
    @OneToMany(mappedBy= "id")
    private List<TestCase> testCases = new ArrayList<>();
    
    @OneToMany(mappedBy = "id")
    private List<Tag> tags = new ArrayList<>();
    
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "questionlist_id")
    private QuestionList questionList;

    @JoinColumn(name = "teacher_id")
    @ManyToOne
    private Teacher owner;
    
    @Column
    private String difficulty;

    @Column
    private Boolean privateQuestion;

}