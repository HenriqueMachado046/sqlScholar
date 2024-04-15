package br.com.sqlScholar.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface IStudentRepository extends JpaRepository<StudentModel, UUID> {

    StudentModel findByUsername(String username);
    Optional <StudentModel> findByEmail(String email);
}
