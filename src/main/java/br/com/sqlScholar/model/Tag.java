package br.com.sqlScholar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue(generator = "UUID")
    @PrimaryKeyJoinColumn
    private UUID id;
    @Column
    private String tag;
    @Column
    private String description;

}
