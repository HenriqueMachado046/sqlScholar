package br.com.sqlScholar.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class TestCase {

    @Id
    @GeneratedValue(generator = "UUID")
    @PrimaryKeyJoinColumn
    private UUID id;
    
    @Column
    private String teste;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
