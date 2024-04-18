package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table
public class Student{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Student(String username,
                        String email,
                        String firstName,
                        String lastName,
                        String password,
                        LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.createdAt = createdAt;
    }

}