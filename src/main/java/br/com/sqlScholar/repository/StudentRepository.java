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
    @Query("SELECT s FROM Student s")
    List<Student> listAll();

}
