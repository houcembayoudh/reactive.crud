package com.training.reactive.crud.exception;

public final class BookNotFoundException extends BookException {

    public BookNotFoundException(final String message) {
        super(message);
    }

}
