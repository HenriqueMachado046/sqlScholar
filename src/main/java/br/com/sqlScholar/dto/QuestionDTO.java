package br.com.sqlScholar.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionDTO {
    private UUID id;        
    private String title;    
    private boolean enabled;    


}
