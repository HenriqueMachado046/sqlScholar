package br.com.sqlScholar.model;


import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Student extends Person{
    public Student(){
        this.setTeacher(false);
    }
}