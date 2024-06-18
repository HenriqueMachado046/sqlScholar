package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
public class Teacher extends Person{

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<Student> students;
    @OneToMany(mappedBy="teacher")
    private List<QuestionList> lists;

    public Teacher() {
        this.setTeacher(true);
    }

    public List<QuestionList> getLists() {
        return lists;
    }

    public void setLists(List<QuestionList> lists) {
        this.lists = lists;
    }

}