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
    @Column
    private boolean isShared;
    @Column
    private String answerSheet;
    
    @OneToMany(mappedBy= "id")
    private List<TestCase> testCases = new ArrayList<>();
    
    @OneToMany(mappedBy = "id")
    private List<Tag> tags = new ArrayList<>();
    
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "questionlist_id")
    )
    private List<QuestionList> questionLists = new ArrayList<>();
    
    @JoinColumn(name = "teacher_id")
    @ManyToOne
    private Teacher owner;
    
    @Column
    private String difficulty;


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isIsShared() {
        return this.isShared;
    }

    public boolean getIsShared() {
        return this.isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }

    public String getAnswerSheet() {
        return this.answerSheet;
    }

    public void setAnswerSheet(String answerSheet) {
        this.answerSheet = answerSheet;
    }

    public List<TestCase> getTestCases() {
        return this.testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public List<Tag> getTag() {
        return this.tags;
    }

    public void setTag(List<Tag> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<QuestionList> getQuestionLists() {
        return this.questionLists;
    }

    public void setQuestionLists(List<QuestionList> questionLists) {
        this.questionLists = questionLists;
    }

    public Teacher getOwner() {
        return this.owner;
    }

    public void setOwner(Teacher owner) {
        this.owner = owner;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}