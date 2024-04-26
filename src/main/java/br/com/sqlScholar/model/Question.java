package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String answerSheet;
//    @ManyToMany
//    @JoinColumn(name = "teacher_id")
    @OneToMany
    @JoinColumn(name = "testcase_id")
    private List<TestCase> testCases;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

}
