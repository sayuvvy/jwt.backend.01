package com.booksample.site.book.model.services;

import com.booksample.site.book.model.*;
import com.booksample.site.book.model.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest bookRequest){

        return Book.builder()
                .id(bookRequest.id())
                .title(bookRequest.title())
                .authorName(bookRequest.authorName())
                .synopsis(bookRequest.synopsis())
                .archived(false)
                .shareable(bookRequest.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book){
        return BookResponse.builder()
                .isbn(book.getIsbn())
                .authorName(book.getAuthorName())
                .owner(book.getOwner().fullName())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .cover(FileUtils.readFromLocation(book.getBookCover()))
                .id(book.getId())
                .title(book.getTitle())
                .shareable(book.isShareable())
                .archived(book.isArchived())
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history){
        return BorrowedBookResponse.builder()
                .isbn(history.getBook().getIsbn())
                .authorName(history.getBook().getAuthorName())
                .owner(history.getBook().getOwner().fullName())
                .synopsis(history.getBook().getSynopsis())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }

}
