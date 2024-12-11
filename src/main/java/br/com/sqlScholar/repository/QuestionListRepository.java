package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.QuestionList;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionListRepository extends JpaRepository<QuestionList, UUID> {
    @Query(value = "SELECT ql from QuestionList ql")
    List<QuestionList> listAll();
    
    @Transactional
    @Query(nativeQuery = true, value = "BEGIN; DELETE FROM questionlist ql WHERE ql.id = :idparam ; COMMIT;")
    void deletar(UUID idparam);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO question (questionlist_id, question_id) values (:idparam1, :idparam2)")
    void insertQuestions(UUID idparam1, UUID idparam2);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM question WHERE questionlist_id = :idparam")
    void deleteQuestions(UUID idparam);

    // @Transactional
    // @Modifying
    // @Query(nativeQuery = true, value = "param")
    // void criarDatabase(String param);
}
