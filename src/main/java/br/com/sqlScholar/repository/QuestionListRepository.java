package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.QuestionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionListRepository extends JpaRepository<QuestionList, UUID> {
    
    @Query(value = "SELECT ql from QuestionList ql")
    List<QuestionList> listAll();

    
    @Query(nativeQuery = true, value = "BEGIN; DELETE FROM question_question_lists qql WHERE qql.questionlist_id = :param ; DELETE FROM questionlist ql WHERE ql.id = :param ; COMMIT;")
    void deletar(@Param("param") UUID param);
}
