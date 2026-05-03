package com.example.Optimize_Database.N1Query.repository;

import com.example.Optimize_Database.N1Query.dto.AuthorDto;
import com.example.Optimize_Database.N1Query.entity.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    // This single query fetches Authors AND their Books in one hit
    @Query("SELECT a FROM Author a JOIN FETCH a.books")
    List<Author> findAllWithBooks();

    //Using EntityGraph

    @EntityGraph(attributePaths = {"books"})
    List<Author> findAll();


    /// Using projection

    @Query("SELECT new com.example.Optimize_Database.N1Query.dto.AuthorDto(a.name, COUNT(b)) " +
            "FROM Author a LEFT JOIN a.books b GROUP BY a.name")
    List<AuthorDto> getAuthorReport();
}
