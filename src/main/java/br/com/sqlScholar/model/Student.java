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
    private int rightAnswers = 0;

    @Column
    private int wrongAnswers = 0;
    
    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
    
    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public Student(){
        this.setTeacher(false);
    }

    public int getRightAnswers() {
        return rightAnswers;
    }
}