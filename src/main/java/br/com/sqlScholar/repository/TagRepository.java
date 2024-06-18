package br.com.sqlScholar.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sqlScholar.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{

    @Query("SELECT t FROM Tag t")
    List<Tag> listAll();
    
}