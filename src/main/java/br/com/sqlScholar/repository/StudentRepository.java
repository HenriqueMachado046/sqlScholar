package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query("SELECT s.firstName, s.lastName, s.rightAnswers, RANK() OVER (ORDER BY s.rightAnswers DESC) as rank FROM Student s")
    List<Student> rankingStudents();
}
