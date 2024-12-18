package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Student;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT e FROM Student e WHERE e.email = ?1")
    Student findByEmailAddress(String email);

    @Query("SELECT e FROM Student e WHERE e.email = ?1 AND e.password = ?2")
    Student findByEmailAndPassword(String email, String password);
    
    @Query("SELECT s FROM Student s")
    List<Student> listAll();
    
    @Query("UPDATE Student s SET s.email =?1, s.firstName =?2, s.lastName = ?3, s.password = ?4, s.username = ?5 WHERE s.id = ?6")
    Student update(String email, String firstName, String lastName, String password, String username, UUID id);

    @Query(nativeQuery = true, value = "SELECT *, RANK() OVER (ORDER BY s.right_answers DESC) as rank FROM Student s LIMIT 3")
    List<Student> rankingStudents();

    @Query("SELECT COUNT (s) FROM Student s")
    List<Integer> countStudents();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="UPDATE student SET right_answers = (right_answers + 1) WHERE id = ?1 ")
    public int updateRightById(UUID id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="UPDATE student SET wrong_answers = (wrong_answers + 1) WHERE id = ?1 ")
    public int updateWrongById(UUID id);

    @Query(nativeQuery = true,value = "SELECT COUNT (wrong_answers) FROM student")
    List<Integer> countWrong();
}
