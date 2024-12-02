package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    UserDetails findByUsername(String username);

    @Query("SELECT e FROM Teacher e WHERE e.email = ?1")
    Teacher findByEmailAddress(String email);

    @Query("SELECT t FROM Teacher t")
    List<Teacher> listAll();

}
