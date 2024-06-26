package br.com.sqlScholar.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DifficultyDTO {
    private String description;
    private String value;
    private boolean enabled;


}
