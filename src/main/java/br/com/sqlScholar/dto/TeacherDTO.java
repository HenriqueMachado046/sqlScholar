package br.com.sqlScholar.dto;

import java.util.UUID;

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
