package br.com.sqlScholar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    @DisplayName("Teste para ver se os campos são iguais")
    void studentEqualsAttributesTest(){
       
        Student student = new Student();
        student.setFirstName("ALUNO_TESTE");
        student.setLastName("SOBRENOME_TESTE");
        student.setEmail("TESTE@TESTE.COM");
        student.setPassword("123");
        student.setTeacher(false);
        assertEquals("ALUNO_TESTE", student.getFirstName());
        assertEquals("SOBRENOME_TESTE", student.getLastName());
        assertEquals("TESTE@TESTE.COM", student.getEmail());
        assertEquals("123", student.getPassword());

    }

    @Test
    @DisplayName("Teste para ver se os campos não são iguais")
    void studentNotEqualsAttributesTest(){
       
        Student student = new Student();
        student.setFirstName("ALUNO_TESTE");
        student.setLastName("SOBRENOME_TESTE");
        student.setEmail("TESTE@TESTE.COM");
        student.setPassword("123");
        student.setTeacher(false);
        assertNotEquals("aluno", student.getFirstName());
        assertNotEquals("sobrenome", student.getLastName());
        assertNotEquals("aluno.sobrenome@email.com", student.getEmail());
        assertNotEquals("senha", student.getPassword());

    }

    @Test
    @DisplayName("Aluno não é igual à outro Aluno")
    void studentsNotEqualsTest(){
       
        Student student = new Student();
        student.setFirstName("ALUNO_TESTE");
        student.setLastName("SOBRENOME_TESTE");
        student.setEmail("TESTE@TESTE.COM");
        student.setPassword("123");
        student.setTeacher(false);

        Student student2 = new Student();
        student2.setFirstName("João");
        student2.setLastName("Silva");
        student2.setEmail("joao.silva@email.com");
        student2.setPassword("senha123");
        student2.setTeacher(true);

        assertNotEquals(student, student2);


    }

    @Test
    @DisplayName("Estudante não é nulo")
    void studentNotNull(){
        Student student = new Student();
        student.setFirstName("ALUNO_TESTE");
        student.setLastName("SOBRENOME_TESTE");
        student.setEmail("TESTE@TESTE.COM");
        student.setPassword("123");
        student.setTeacher(false);
        
        assertNotNull(student);

    }

    
}
