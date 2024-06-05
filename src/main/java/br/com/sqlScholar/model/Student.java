package br.com.sqlScholar.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Student extends Person{

    @ManyToMany(mappedBy = "students")
    List<Teacher> teachers;

    public Student(){
        this.setTeacher(false);
    }
}