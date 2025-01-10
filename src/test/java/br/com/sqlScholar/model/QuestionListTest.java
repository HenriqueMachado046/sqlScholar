package br.com.sqlScholar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class QuestionListTest {

    @Test
    @DisplayName("QuestionList tem os atributos corretos.")
    void questionListAttributesEqualsTest(){
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

        QuestionList questionList = new QuestionList();
        questionList.setDatabaseName("Banco teste");
        questionList.setDatabaseScript("CREATE TABLE pessoa");
        questionList.setDescription("TEST_DESCRIPTION");
        questionList.setOwner(teacher);
        questionList.setTitle("TEST_TITLE");
        questionList.getQuestions().add(question);
        questionList.setPrivate(false);

        assertEquals("Banco teste", questionList.getDatabaseName());
        assertEquals("CREATE TABLE pessoa", questionList.getDatabaseScript());
        assertEquals("TEST_DESCRIPTION", questionList.getDescription());
        assertEquals(teacher, questionList.getOwner());
        assertEquals("TEST_TITLE", questionList.getTitle());
        assertEquals(question, questionList.getQuestions().get(0));
        assertEquals(false, questionList.isPrivate());
    }

    @Test
    @DisplayName("QuestionList não tem os atributos corretos.")
    void questionListAttributesNotEqualsTest(){
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

        Teacher teacher2 = new Teacher();
        teacher.setFirstName("João");
        teacher.setLastName("Silva");
        teacher.setEmail("joao.silva@email.com");
        teacher.setPassword("senha123");
        teacher.setTeacher(true);

        Question question2 = new Question();
        question.setDescription("Descrição not equals");
        question.setOwner(teacher2);
        question.setDifficulty(Difficulty.DIFICIL.toString());
        question.setSql("SELECT * FROM funcionario");
        question.setTitle("Título oficial");

        QuestionList questionList = new QuestionList();
        questionList.setDatabaseName("Banco teste");
        questionList.setDatabaseScript("CREATE TABLE pessoa");
        questionList.setDescription("TEST_DESCRIPTION");
        questionList.setOwner(teacher);
        questionList.setTitle("TEST_TITLE");
        questionList.getQuestions().add(question);
        questionList.setPrivate(false);

        assertNotEquals("Banco de exemplo", questionList.getDatabaseName());
        assertNotEquals("CREATE TABLE funcionario", questionList.getDatabaseScript());
        assertNotEquals("Descrição", questionList.getDescription());
        assertNotEquals(teacher2, questionList.getOwner());
        assertNotEquals("Título", questionList.getTitle());
        assertNotEquals(question2, questionList.getQuestions().get(0));
        assertNotEquals(true, questionList.isPrivate());
    }

    @Test
    @DisplayName("QuestionList não é igual a outra QuestionLIst.")
    void questionListNotEqualsTest(){
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

        Teacher teacher2 = new Teacher();
        teacher.setFirstName("João");
        teacher.setLastName("Silva");
        teacher.setEmail("joao.silva@email.com");
        teacher.setPassword("senha123");
        teacher.setTeacher(true);

        Question question2 = new Question();
        question.setDescription("Descrição not equals");
        question.setOwner(teacher2);
        question.setDifficulty(Difficulty.DIFICIL.toString());
        question.setSql("SELECT * FROM funcionario");
        question.setTitle("Título oficial");

        QuestionList questionList = new QuestionList();
        questionList.setDatabaseName("Banco teste");
        questionList.setDatabaseScript("CREATE TABLE pessoa");
        questionList.setDescription("TEST_DESCRIPTION");
        questionList.setOwner(teacher);
        questionList.setTitle("TEST_TITLE");
        questionList.getQuestions().add(question);
        questionList.setPrivate(false);

        QuestionList questionList2 = new QuestionList();
        questionList.setDatabaseName("Banco de Exemplo");
        questionList.setDatabaseScript("CREATE TABLE funcionario");
        questionList.setDescription("Descrição verdadeira");
        questionList.setOwner(teacher2);
        questionList.setTitle("Título oficial da lista");
        questionList.getQuestions().add(question2);
        questionList.setPrivate(true);

        assertNotEquals(questionList.getDatabaseName(), questionList2.getDatabaseName());
        assertNotEquals(questionList.getDatabaseScript(), questionList2.getDatabaseScript());
        assertNotEquals(questionList.getDescription(), questionList2.getDescription());
        assertNotEquals(questionList.getOwner(), questionList2.getOwner());
        assertNotEquals(questionList.getQuestions(), questionList2.getQuestions());
        assertNotEquals(questionList.getTitle(), questionList2.getTitle());
        assertNotEquals(questionList.isPrivate(), questionList2.isPrivate());
        assertNotEquals(questionList, questionList2);
    }

    @Test
    @DisplayName("QuestionList tem professor e questão.")
    void questionListHasTeacherAndQuestionTest(){
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

        QuestionList questionList = new QuestionList();
        questionList.setDatabaseName("Banco teste");
        questionList.setDatabaseScript("CREATE TABLE pessoa");
        questionList.setDescription("TEST_DESCRIPTION");
        questionList.setOwner(teacher);
        questionList.setTitle("TEST_TITLE");
        questionList.getQuestions().add(question);
        questionList.setPrivate(false);

        assertTrue(teacher.equals(questionList.getOwner()));
        assertTrue(question.equals(questionList.getQuestions().get(0)));
    }
}
