package br.com.sqlScholar.repository;

import br.com.sqlScholar.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, UUID>{

    @Query("SELECT t FROM TestCase t")
    List<TestCase> listAll();

    //@Query("SELECT q FROM ")
}