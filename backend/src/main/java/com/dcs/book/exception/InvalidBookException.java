package com.dcs.book.exception;

import com.dcs.book.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an {@link Book} instance contains invalid field values
 * (e.g {@link Book#id} is null, invalid amount, etc.)
 *
 * @see Book
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookException extends RuntimeException {
    public InvalidBookException(Book book) {
        super("Invalid Book field values [" + book + "]");
    }
}
