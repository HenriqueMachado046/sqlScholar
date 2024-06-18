package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query("SELECT q FROM Question q")
    List<Question> listAll();

}
