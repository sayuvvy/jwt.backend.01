package com.booksample.site.book.model;

import com.booksample.site.book.model.BaseEntity;
import com.booksample.site.book.model.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback extends BaseEntity {
    private Double note;
    private String comment;
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
}
