package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    
    @Query("SELECT q FROM Question q WHERE q.owner =?1")
    List<Question> listAllByTeacher(UUID id);

    @Query("SELECT COUNT (q) FROM Question q ")
    List<Integer> countQuestions();

    @Query("SELECT COUNT (q) FROM Question q JOIN Teacher t ON t.id = ?1")
    List<Integer> countQuestionsTeachers(UUID id);
}
