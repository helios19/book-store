package com.dcs.book.repository;

import com.dcs.book.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE;
import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE_2;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    BookRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }
    @Test
    public void shouldNotFindAnyBookIfRepositoryIsEmpty() {
        Iterable books = repository.findAll();
        assertThat(books).isEmpty();
    }

    @Test
    public void shouldSaveBook() {
        Book book = repository.save(BOOK_SAMPLE);
        assertThat(book).hasFieldOrPropertyWithValue("title", "book 1");
        assertThat(book).hasFieldOrPropertyWithValue("country", "United States");
    }

    @Test
    public void shouldFindByIdBook() {
        Book bookSaved = repository.save(BOOK_SAMPLE);
        Optional<Book> book = repository.findById(bookSaved.getId());
        assertThat(book.isEmpty()).isFalse();
    }

    @Test
    public void shouldFindAllBooks() {
        Book t1 = BOOK_SAMPLE;
        t1 = repository.save(t1);
        Book t2 = BOOK_SAMPLE_2;
        t2 = repository.save(t2);
        Iterable books = repository.findAll();
        assertThat(books).hasSize(2).contains(t1, t2);
    }

    @Test
    public void shouldDeleteBook() {
        Book book = repository.save(BOOK_SAMPLE);
        repository.delete(book);
        Optional<Book> bookDeleted = repository.findById(book.getId());
        assertThat(bookDeleted.isEmpty()).isTrue();
    }
}
