package br.com.sqlScholar.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IStudentRepository extends JpaRepository<StudentModel, UUID> {

    StudentModel findByUsername(String username);
}
