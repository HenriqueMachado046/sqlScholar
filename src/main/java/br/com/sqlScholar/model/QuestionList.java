package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "questionlist")
@Getter
@Setter
public class QuestionList {
    @Id
    @GeneratedValue(generator = "UUID")
    @PrimaryKeyJoinColumn
    private UUID id;
    
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "questionList")
    private List<Question> questions = new ArrayList<>();
    
    @Column
    private String databaseName;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column
    private String title;
    
    @Column
    private String databaseScript;

    @Column
    private String description;
    
    @Column
    private boolean isPrivate;    
    
    @ManyToOne
    private Teacher owner;

}