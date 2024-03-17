package com.dcs.book.exception;

import com.dcs.book.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when no {@link Book} resource cannot be found.
 *
 * @see Book
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String bookId) {
        super("No book found in database for book id:" + bookId);
    }

    public BookNotFoundException() {
        super("No book found in database.");
    }
}
