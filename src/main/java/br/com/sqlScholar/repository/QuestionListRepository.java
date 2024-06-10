package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.QuestionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionListRepository extends JpaRepository<QuestionList, UUID> {
    @Query("SELECT ql from QuestionList ql")
    List<QuestionList> listAll();
}
