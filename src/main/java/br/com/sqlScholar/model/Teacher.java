package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Data
public class Teacher extends Person{

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<Student> students = new ArrayList<>();
    
    @OneToMany(mappedBy="teacher")
    private List<QuestionList> lists = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Question> ownedQuestions = new ArrayList<>();


    public Teacher() {
        this.setTeacher(true);
    }

    
}