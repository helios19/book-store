package com.dcs.book.service;

import com.dcs.common.utils.ClassUtils;
import com.dcs.book.model.Book;
import com.dcs.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class providing CRUD operations and caching logic for {@link Book} resource.
 *
 * @see BookRepository
 */
@Service
@Slf4j
@CacheConfig(cacheNames = ClassUtils.BOOKS_COLLECTION_NAME)
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(allEntries = true)
    public void delete(Book book) {
        repository.delete(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(allEntries = true)
    public Book save(Book book) {
        return repository.save(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<Book> findAll() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<Book> findAll(Pageable pageable) {
        Page<Book> books = repository.findAll(pageable);
        return books.getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<Book> findAll(Sort by) {
        return repository.findAll(by);
    }

    /**
     * Sets an {@link BookRepository} instance.
     *
     * @param repository Book repository instance
     */
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }
}
