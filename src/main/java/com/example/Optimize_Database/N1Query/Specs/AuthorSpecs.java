package com.example.Optimize_Database.N1Query.Specs;

import com.example.Optimize_Database.N1Query.entity.Author;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecs {

    public static Specification<Author> fetchBooksAndFilterByName(String authorName) {
        return (root, query, cb) -> {

            // This ensures books are loaded in the same query

            root.fetch("books", JoinType.LEFT);

            // 2. Filter logic
            if (authorName == null || authorName.isEmpty()) {
                return cb.conjunction(); // returns "1=1" (no filter)
            }
            return cb.like(root.get("name"), "%" + authorName + "%");
        };
    }

    public static Specification<Author> fetchBooksSafely() {
        return (root, query, cb) -> {
            // Check if the query is a data query (not a count query)
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("books", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }
}
