package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    
    @Query("SELECT q FROM Question q")
    List<Question> listAll();

    @Query("SELECT q FROM Question q WHERE q.owner = ?1 OR q.isShared = TRUE")
    List<Question> listAllSharedAndOwned(Teacher owner);

    @Query("SELECT q FROM Question q WHERE q.owner =?1")
    List<Question> listAllByTeacher();

    @Query("SELECT q FROM Question q WHERE q.isShared = TRUE")
    List<Question> listAllSharedQuestions();
}
