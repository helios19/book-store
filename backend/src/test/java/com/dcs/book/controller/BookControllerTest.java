package com.dcs.book.controller;

import com.dcs.book.dto.BookDto;
import com.dcs.book.exception.BookNotFoundException;
import com.dcs.book.model.Book;
import com.dcs.book.service.BookService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE;
import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    BookController controller;

    @Test
    public void shouldReturnABook() {

        // Given
        when(bookService.findById(any()))
                .thenReturn(Optional.of(BOOK_SAMPLE));

        // When
        ResponseEntity<BookDto> response = controller.get(1L);

        // Then
        assertThat(response).isNotNull();
        verify(bookService, times(1)).findById(any());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void shouldReturnAllBooks() {

        // Given
        when(bookService.findAll(any(Sort.class)))
                .thenReturn(Lists.newArrayList(BOOK_SAMPLE));

        // When
        ResponseEntity<List<BookDto>> response = controller.getAllBooks(new String[]{"title", "asc"});

        // Then
        assertThat(response).isNotNull();
        verify(bookService, times(1)).findAll(any(Sort.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void shouldSaveBook() {

        // Given
        when(bookService.save(any(Book.class)))
                .thenReturn(BOOK_SAMPLE);

        // When
        ResponseEntity<BookDto> response = controller.save(BOOK_SAMPLE);

        // Then
        assertThat(response).isNotNull();
        verify(bookService, times(1)).save(BOOK_SAMPLE);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void shouldUpdateBook() {

        // Given
        when(bookService.save(any(Book.class)))
                .thenReturn(BOOK_SAMPLE);
        when(bookService.findById(any()))
                .thenReturn(Optional.of(BOOK_SAMPLE_2));

        // When
        ResponseEntity<BookDto> response = controller.update(BOOK_SAMPLE, BOOK_SAMPLE_2.getId());

        // Then
        assertThat(response).isNotNull();
        verify(bookService, times(1)).findById(any());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void shouldDeleteBook() {

        // Given
        when(bookService.findById(any()))
                .thenReturn(Optional.of(BOOK_SAMPLE));

        // When
        ResponseEntity<BookDto> response = controller.delete(BOOK_SAMPLE.getId());

        // Then
        assertThat(response).isNotNull();
        verify(bookService, times(1)).findById(any());
        verify(bookService, times(1)).delete(BOOK_SAMPLE);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void shouldThrowBookNotFoundExceptionWhenNoBookExists() {
        // Given
        when(bookService.findAll(any(Sort.class)))
                .thenReturn(Lists.newArrayList());

        // When
        BookNotFoundException thrown = assertThrows(
                BookNotFoundException.class,
                () -> controller.getAllBooks(new String[]{"title", "asc"}),
                "Expected getAllBookSummaryReport() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No book found in database."));
    }
}
