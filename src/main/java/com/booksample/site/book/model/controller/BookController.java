package com.booksample.site.book.model.controller;

import com.booksample.site.book.model.BookRequest;
import com.booksample.site.book.model.BookResponse;
import com.booksample.site.book.model.BorrowedBookResponse;
import com.booksample.site.book.model.PageResponse;
import com.booksample.site.book.model.services.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name="Book")
public class BookController {
    private final BookService bookService;
    @PostMapping("")
    public ResponseEntity<Integer> createBook(
            @Valid @RequestBody BookRequest bookRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(bookService.save(bookRequest, authentication));
    }
    @GetMapping("{book-id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable("book-id") Integer bookId
    ){
        return ResponseEntity.ok(bookService.findById(bookId));
    }
    @GetMapping
    public ResponseEntity<PageResponse> findAllBooks(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "0", required = false) int size,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.findAllBooks(page, size, loggedinUser));
    }
    @GetMapping("/owner")
    public ResponseEntity<PageResponse> findAllBooksByOwner(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "0", required = false) int size,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.findAllBooks(page, size, loggedinUser));
    }
    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "0", required = false) int size,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, loggedinUser));
    }
    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="size", defaultValue = "0", required = false) int size,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, loggedinUser));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateSahreableStatus(
            @PathVariable("book-id") Integer bookid,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.updateShareadbleStatus(bookid, loggedinUser));
    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> updateArchiveStatus(
            @PathVariable("book-id") Integer bookid,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.updateArchivedStatus(bookid, loggedinUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("book-id") Integer bookid,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.borrowBook(bookid, loggedinUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowedBook(
            @PathVariable("book-id") Integer bookid,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookid, loggedinUser));
    }

    @PatchMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<Integer> returnApproveBorrowedBook(
            @PathVariable("book-id") Integer bookid,
            Authentication loggedinUser
    ){
        return ResponseEntity.ok(bookService.returnApproveBorrowedBook(bookid, loggedinUser));
    }

    @PostMapping(value="/cover/{book-id}", consumes="multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPic(
            @PathVariable("book-id") Integer bookId,
            @RequestPart("file") MultipartFile file,
            Authentication loggedinUser
    ){
      bookService.uploadBookCoverPic(file, loggedinUser, bookId);
      return ResponseEntity.accepted().build();
    }
}
