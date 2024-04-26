package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class Teacher extends Person{

    public Teacher() {
        this.setTeacher(true);
    }

}