package br.com.sqlScholar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sqlScholar.repository.TeacherRepository;

public class TeacherTest {
    @Autowired
    TeacherRepository teacherRepository;


    @Test
    @DisplayName("Teste para ver se os campos são iguais")
    void teacherEqualsAttributesTest(){
       
        Teacher teacher = new Teacher();
        teacher.setFirstName("PROFESSOR_TESTE");
        teacher.setLastName("SOBRENOME_TESTE");
        teacher.setEmail("TESTE@TESTE.COM");
        teacher.setPassword("123");
        teacher.setTeacher(true);
        assertEquals("PROFESSOR_TESTE", teacher.getFirstName());
        assertEquals("SOBRENOME_TESTE", teacher.getLastName());
        assertEquals("TESTE@TESTE.COM", teacher.getEmail());
        assertEquals("123", teacher.getPassword());

    }

    @Test
    @DisplayName("Testa se os campos não são iguais.")
    void teacherNotEqualsAttributesTest(){
       
        Teacher teacher = new Teacher();
        teacher.setFirstName("PROFESSOR_TESTE");
        teacher.setLastName("SOBRENOME_TESTE");
        teacher.setEmail("TESTE@TESTE.COM");
        teacher.setPassword("123");
        teacher.setTeacher(true);
        assertNotEquals("João", teacher.getFirstName());
        assertNotEquals("Silva", teacher.getLastName());
        assertNotEquals("joao.silva@email.com", teacher.getEmail());
        assertNotEquals("senha", teacher.getPassword());

    }

    @Test
    @DisplayName("Professores não devem ser iguais.")
    void teacherNotEqualsTest(){
        Teacher teacher = new Teacher();
        teacher.setFirstName("PROFESSOR_TESTE");
        teacher.setLastName("SOBRENOME_TESTE");
        teacher.setEmail("TESTE@TESTE.COM");
        teacher.setPassword("123");
        teacher.setTeacher(true);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("João");
        teacher2.setLastName("Silva");
        teacher2.setEmail("joao.silva@email.com");
        teacher2.setPassword("senha123");
        teacher2.setTeacher(true);

        assertNotEquals(teacher, teacher2);

    }

    @Test
    @DisplayName("Professor tem uma questão")
    void teacherQuestionAddTest(){
        Teacher teacher = new Teacher();
        teacher.setFirstName("PROFESSOR_TESTE");
        teacher.setLastName("SOBRENOME_TESTE");
        teacher.setEmail("TESTE@TESTE.COM");
        teacher.setPassword("123");
        teacher.setTeacher(true);

        Question question = new Question();
        question.setDescription("DESCRICAO_TESTE");
        question.setOwner(teacher);
        question.setDifficulty(Difficulty.FACIL.toString());
        question.setSql("SELECT * FROM pessoa");
        question.setTitle("TITLE_TEST");
        teacher.getOwnedQuestions().add(question);

        assertTrue((teacher.getOwnedQuestions().size() >= 1));
        
    }

}
