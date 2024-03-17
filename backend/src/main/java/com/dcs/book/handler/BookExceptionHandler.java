package com.dcs.book.handler;

import com.dcs.book.exception.BookNotFoundException;
import com.dcs.book.exception.InvalidBookException;
import com.dcs.book.exception.InvalidParameterException;
import com.dcs.common.handler.GlobalExceptionHandler;
import com.dcs.book.model.Book;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler class used for catching any {@link Book} related exceptions
 * and transforming them into HATEOAS JSON message.
 *
 * @see VndErrors
 * @see Book
 * @see InvalidBookException
 * @see InvalidParameterException
 * @see BookNotFoundException
 */
@ControllerAdvice
public class BookExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidBookException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors handleInvalidBookException(InvalidBookException ex) {
        return getVndErrors(ex);
    }

    @ResponseBody
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors handleInvalidParameterException(InvalidParameterException ex) {
        return getVndErrors(ex);
    }

    @ResponseBody
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors handleBookNotFoundException(BookNotFoundException ex) {
        return getVndErrors(ex);
    }

}
