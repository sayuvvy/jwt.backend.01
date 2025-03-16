package com.booksample.site.book.model.Repository;

import com.booksample.site.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepostiroy extends JpaRepository<Book, Integer> , JpaSpecificationExecutor<Book> {

    @Query("""
            SELECT book
            FROM Book book
            WHERE book.owner.id = :id
            AND book.archived = false
            AND book.shareable = true
            """)
    Page<Book> findAllDispalyBooks(Pageable pageable, Integer id);

}
