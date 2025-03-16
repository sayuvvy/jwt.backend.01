package com.booksample.site.book.model;

import com.booksample.site.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String owner;
    private byte[] cover;
    private double rate;
    private boolean shareable;
    private boolean archived;
}
