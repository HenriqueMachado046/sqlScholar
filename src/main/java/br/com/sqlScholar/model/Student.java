package br.com.sqlScholar.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Student extends Person{

    @ManyToMany(mappedBy = "students")
    List<Teacher> teachers;

    @Column
    private int rightAnswers;

    @Column
    private int wrongAnswers;
    
    public Student(){
        this.setTeacher(false);
    }
}