package com.dcs.book.service;

import com.dcs.book.model.Book;
import com.dcs.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE;
import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    public void setup() {
        book1 = BOOK_SAMPLE;
        book2 = BOOK_SAMPLE_2;
    }

    @Test
    public void givenBookWhenSavedThenReturnBookObject() {
        // Given
        given(bookRepository.save(book1)).willReturn(book1);

        // When
        Book t = bookService.save(book1);

        // Then
        assertThat(t).isNotNull();
        verify(bookRepository, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void givenBookWhenDeletedThenDeleteObject() {
        // Given

        // When
        bookService.delete(book1);

        // Then
        verify(bookRepository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void givenBookWhenFindByIdInvokedThenBookObject() {
        // Given
        given(bookRepository.findById(any())).willReturn(Optional.of(book1));

        // When
        bookService.findById(book1.getId());

        // Then
        verify(bookRepository, times(1)).findById(any());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void givenListOfBooksWhenFindAllInvokedThenReturnBookList() {
        // Given
        given(bookRepository.findAll()).willReturn(List.of(book1, book2));

        // When
        List<Book> bookList = bookService.findAll();

        // Then
        assertThat(bookList).isNotNull();
        assertThat(bookList.size()).isEqualTo(2);
        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);
    }
}
