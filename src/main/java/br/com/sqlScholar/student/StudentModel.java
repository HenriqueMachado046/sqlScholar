package br.com.sqlScholar.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_students")
public class StudentModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

}