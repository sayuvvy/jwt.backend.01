package com.booksample.site.book.model.services;

import com.booksample.site.book.model.*;
import com.booksample.site.book.model.Repository.BookRepostiroy;
import com.booksample.site.book.model.Repository.BookTransactionHistoryRepostiroy;
import com.booksample.site.book.model.exception.OperationNotPermittedException;
import com.booksample.site.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepostiroy bookRepostiroy;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepostiroy  bookTransactionHistoryRepostiroy;
    private final FileStorageService fileStorageService;
    public Integer save(BookRequest bookRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Book book = bookMapper.toBook(bookRequest);
        book.setOwner(user);
        return bookRepostiroy.save(book).getId();
    }
    public BookResponse findById(Integer bookId) {
        return bookRepostiroy.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(()-> new EntityNotFoundException("Book not found ::" + bookId));
    }
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepostiroy.findAllDispalyBooks(pageable, user.getId());
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }
    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepostiroy.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> histories = bookTransactionHistoryRepostiroy.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> borrowedBookResponses = histories.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                borrowedBookResponses,
                histories.getNumber(),
                histories.getSize(),
                histories.getTotalElements(),
                histories.getTotalPages(),
                histories.isFirst(),
                histories.isLast()
        );
    }
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> histories = bookTransactionHistoryRepostiroy.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> borrowedBookResponses = histories.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                borrowedBookResponses,
                histories.getNumber(),
                histories.getSize(),
                histories.getTotalElements(),
                histories.getTotalPages(),
                histories.isFirst(),
                histories.isLast()
        );
    }
    public Integer updateShareadbleStatus(Integer bookId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Book book = bookRepostiroy.findById(bookId)
                        .orElseThrow(()-> new EntityNotFoundException("Book not found"));
        if (!Objects.equals(book.getOwner().getBooks(), user.getId())){
            throw new OperationNotPermittedException("Cannot be carried out");
        }
        book.setShareable(!book.isShareable());
        return bookRepostiroy.save(book).getId();
    }
    public Integer updateArchivedStatus(Integer bookId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Book book = bookRepostiroy.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book not found"));
        if (!Objects.equals(book.getOwner().getBooks(), user.getId())){
            throw new OperationNotPermittedException("Cannot be carried out");
        }
        book.setArchived(!book.isArchived());
        return bookRepostiroy.save(book).getId();
    }
    public Integer borrowBook(Integer bookId, Authentication loggedinUser) {
        User user = (User) loggedinUser.getPrincipal();
        Book book = bookRepostiroy.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book not found"));
        if (book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("Book either archived or not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("Cannot borrow your own book");
        }
        final boolean isAlreadyBorrowed = bookTransactionHistoryRepostiroy.isAlreadyBorrowedByUser(bookId, user.getId());
        if (isAlreadyBorrowed){
            throw new OperationNotPermittedException("Book is already borrowed");
        }
        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return bookTransactionHistoryRepostiroy.save(bookTransactionHistory).getId();
    }
    public Integer returnBorrowedBook(Integer bookId, Authentication loggedinUser) {
        User user = (User) loggedinUser.getPrincipal();
        Book book = bookRepostiroy.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book not found"));
        if (book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("Book either archived or not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("Cannot borrowor return your own book");
        }
        BookTransactionHistory borrowedBook = bookTransactionHistoryRepostiroy.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(()-> new OperationNotPermittedException("You did not borrow and hence cannot return"));
        borrowedBook.setReturned(true);
        return bookTransactionHistoryRepostiroy.save(borrowedBook).getId();
    }
    public Integer returnApproveBorrowedBook(Integer bookId, Authentication loggedinUser) {
        User user = (User) loggedinUser.getPrincipal();
        Book book = bookRepostiroy.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book not found"));
        if (book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("Book either archived or not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("Cannot borrowor return your own book");
        }
        BookTransactionHistory borrowedBook = bookTransactionHistoryRepostiroy.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(()-> new OperationNotPermittedException("You did not borrow and hence cannot return"));
        borrowedBook.setReturned(true);
        return bookTransactionHistoryRepostiroy.save(borrowedBook).getId();
    }
    public void uploadBookCoverPic(MultipartFile file, Authentication loggedinUser, Integer bookId){
        User user = (User) loggedinUser.getPrincipal();
        Book book = bookRepostiroy.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book not found"));
        var bookCover = fileStorageService.saveFile(file, user.getId());
        book.setBookCover(bookCover);
        bookRepostiroy.save(book);
    }
}