package com.booksample.site.book.model.Repository;

import com.booksample.site.book.model.Book;
import com.booksample.site.book.model.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionHistoryRepostiroy extends JpaRepository<BookTransactionHistory, Integer> {
    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.id = :id
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer id);
    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.id = :id
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer id);
    @Query("""
            SELECT (COUNT(*) > 0) AS isBorrowed
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.user.id = :id
            AND history.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer id);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.user.id = :id
            AND history.returned = false
            AND history.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer id);
}
