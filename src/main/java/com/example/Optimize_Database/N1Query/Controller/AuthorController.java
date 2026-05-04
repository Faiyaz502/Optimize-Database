package com.example.Optimize_Database.N1Query.Controller;

import com.example.Optimize_Database.N1Query.dto.AuthorDto;
import com.example.Optimize_Database.N1Query.entity.Author;
import com.example.Optimize_Database.N1Query.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Test JOIN FETCH
    @GetMapping("/join-fetch")
    public ResponseEntity<List<Author>> getWithJoinFetch() {
        return ResponseEntity.ok(authorService.getAuthorsWithJoinFetch());
    }

    // Test EntityGraph
    @GetMapping("/entity-graph")
    public ResponseEntity<List<Author>> getWithEntityGraph() {
        return ResponseEntity.ok(authorService.getAuthorsWithEntityGraph());
    }

    // Test Projections (Return DTO)
    @GetMapping("/report")
    public ResponseEntity<List<AuthorDto>> getReport() {
        return ResponseEntity.ok(authorService.getAuthorReport());
    }

    // Test JPA Specifications
    @GetMapping("/spec")
    public ResponseEntity<List<Author>> getWithSpecs(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(authorService.getAuthorsWithSpecs(name));
    }
}
