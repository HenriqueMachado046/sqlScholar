package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.model.Teacher;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("SELECT t FROM Teacher t WHERE t.email = ?1")
    public Teacher findByEmailAddress(String email);


    @Query("SELECT t FROM Teacher t")
    public List<Teacher> listAll();

    @Query("SELECT t FROM Teacher t WHERE t.email = ?1 AND t.password = ?2")
    public Teacher findByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="UPDATE teacher SET solved_questions = (solved_questions + 1) WHERE id = ?1 ")
    public int updateCounterById(UUID id);
}
