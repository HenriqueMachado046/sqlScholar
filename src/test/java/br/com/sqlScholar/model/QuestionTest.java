package br.com.sqlScholar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuestionTest {

    @Test
    @DisplayName("Questão tem um professor.")
    void questionTeacherTest(){
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
        assertTrue((question.getOwner() != null));
    }

    @Test
    @DisplayName("Questão tem os mesmos campos")
    void questionEqualsAttributesTest(){

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

        assertEquals("DESCRICAO_TESTE", question.getDescription());
        assertEquals(Difficulty.FACIL.toString(), question.getDifficulty());
        assertEquals(teacher, question.getOwner());
        assertEquals("SELECT * FROM pessoa", question.getSql());
        assertEquals("TITLE_TEST", question.getTitle());


    }

    @Test
    @DisplayName("Questão não tem os mesmos campos")
    void questionNotEqualsAttributesTest(){

        Teacher teacher = new Teacher();
        teacher.setFirstName("PROFESSOR_TESTE");
        teacher.setLastName("SOBRENOME_TESTE");
        teacher.setEmail("TESTE@TESTE.COM");
        teacher.setPassword("123");
        teacher.setTeacher(true);

        Teacher teacher2 = new Teacher();
        teacher.setFirstName("João");
        teacher.setLastName("Silva");
        teacher.setEmail("joao.silva@email.com");
        teacher.setPassword("senha123");
        teacher.setTeacher(true);

        Question question = new Question();
        question.setDescription("DESCRICAO_TESTE");
        question.setOwner(teacher);
        question.setDifficulty(Difficulty.FACIL.toString());
        question.setSql("SELECT * FROM pessoa");
        question.setTitle("TITLE_TEST");

        assertNotEquals("Descrição not equals", question.getDescription());
        assertNotEquals(Difficulty.DIFICIL.toString(), question.getDifficulty());
        assertNotEquals(teacher2, question.getOwner());
        assertNotEquals("SELECT * FROM funcionario", question.getSql());
        assertNotEquals("Título oficial", question.getTitle());


    }

    @Test
    @DisplayName("Questão é diferentente de outra questão")
    void questionNotEqualsTest(){
        Teacher teacher = new Teacher();
        teacher.setFirstName("PROFESSOR_TESTE");
        teacher.setLastName("SOBRENOME_TESTE");
        teacher.setEmail("TESTE@TESTE.COM");
        teacher.setPassword("123");
        teacher.setTeacher(true);

        Teacher teacher2 = new Teacher();
        teacher.setFirstName("João");
        teacher.setLastName("Silva");
        teacher.setEmail("joao.silva@email.com");
        teacher.setPassword("senha123");
        teacher.setTeacher(true);

        Question question = new Question();
        question.setDescription("DESCRICAO_TESTE");
        question.setOwner(teacher);
        question.setDifficulty(Difficulty.FACIL.toString());
        question.setSql("SELECT * FROM pessoa");
        question.setTitle("TITLE_TEST");

        Question question2 = new Question();
        question.setDescription("Descrição not equals");
        question.setOwner(teacher2);
        question.setDifficulty(Difficulty.DIFICIL.toString());
        question.setSql("SELECT * FROM funcionario");
        question.setTitle("Título oficial");

        assertNotEquals(question, question2);
        
    }

}
