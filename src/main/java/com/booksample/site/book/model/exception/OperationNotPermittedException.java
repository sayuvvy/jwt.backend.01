package com.booksample.site.book.model.exception;


public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(String msg) {
        super(msg);
    }
}
