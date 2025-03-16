package com.booksample.site.book.model;

import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withOwnerId(Integer ownerid){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerid);
    }
}
