package com.example.Optimize_Database.N1Query.service;

import com.example.Optimize_Database.N1Query.Specs.AuthorSpecs;
import com.example.Optimize_Database.N1Query.dto.AuthorDto;
import com.example.Optimize_Database.N1Query.entity.Author;
import com.example.Optimize_Database.N1Query.repository.AuthorRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Strategy 1-- JOIN FETCH (JPQL)
    public List<Author> getAuthorsWithJoinFetch() {
        return authorRepository.findAllWithBooks();
    }



    // Strategy 2-- Entity Graph
    public List<Author> getAuthorsWithEntityGraph() {
        return authorRepository.findAll();
    }



    // Strategy 3 --Projections (DTOs)
    public List<AuthorDto> getAuthorReport() {
        return authorRepository.getAuthorReport();
    }



    // Strategy 4--- JPA Specification (Dynamic Fetching)
    public List<Author> getAuthorsWithSpecs(String name) {
        Specification<Author> spec = Specification
                .where(AuthorSpecs.fetchBooksSafely())
                .and(authorNameContains(name));

        return authorRepository.findAll(spec);
    }

    private Specification<Author> authorNameContains(String name) {
        return (root, query, cb) -> (name == null || name.isEmpty())
                ? cb.conjunction()
                : cb.like(root.get("name"), "%" + name + "%");
    }
}
