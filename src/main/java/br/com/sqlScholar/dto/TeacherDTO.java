package br.com.sqlScholar.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.model.Teacher;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeacherDTO  {
    private UUID id;        
    private String firstName;    
    private String lastName;
    private boolean isOwner;
}
