package com.dcs.book.service;

import com.dcs.book.model.Book;
import com.dcs.book.repository.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Service interface providing method declarations for CRUD operations for the {@link Book} resource.
 *
 * @see Book
 * @see BookServiceImpl
 */
public interface BookService {
    /**
     * Deletes an {@link Book} instance.
     *
     * @param book Book object to delete
     */
    void delete(Book book);

    /**
     * Saves an {@link Book} instance.
     *
     * @param book Book object to save
     * @return Saved book
     */
    Book save(Book book);

    /**
     * Returns an {@link Optional<Book>} instance given {@code id} argument.
     *
     * @param id Book's identifier
     * @return Optional book
     */
    Optional<Book> findById(Long id);

    /**
     * Returns a list of all {@link Book}.
     *
     * @return List of books found
     */
    List<Book> findAll();

    /**
     * Return a list of {@link Book} given {@link Pageable} argument.
     *
     * @param pageable Pageable argument
     * @return List of books found
     */
    List<Book> findAll(Pageable pageable);

    /**
     * Return a list of {@link Book} given {@link Sort} argument.
     *
     * @param by Sort argument
     * @return List of books found
     */
    List<Book> findAll(Sort by);

    /**
     * Sets a {@link BookRepository} instance.
     *
     * @param repository Book repository instance
     */
    void setRepository(BookRepository repository);
}
