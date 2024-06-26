package br.com.sqlScholar.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Student extends Person{

    @ManyToMany(mappedBy = "students")
    List<Teacher> teachers;

    public Student(){
        this.setTeacher(false);
    }
}