package com.dcs.cache;

import com.dcs.common.utils.ClassUtils;
import com.dcs.book.model.Book;
import com.dcs.book.repository.BookRepository;
import com.dcs.book.service.BookServiceImpl;
import com.google.common.collect.Lists;
import com.dcs.common.ClassTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test"})
@SpringBootTest(classes = BookCacheIT.TestCacheConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookCacheIT {
    @LocalServerPort
    private int port;

    @Autowired
    @InjectMocks
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository repository;

    @Autowired
    private CacheManager cacheManager;

    private Book sampleBook = ClassTestUtils.BOOK_SAMPLE;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bookService.save(sampleBook);
        bookService.setRepository(repository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // reset book cache
        cacheManager.getCache(ClassUtils.BOOKS_COLLECTION_NAME).clear();
    }

    @Test
    @DirtiesContext
    public void shouldFindBookByIdFromCache() {

        // given
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(sampleBook));

        Long id = 1L;
        Cache bookCache = cacheManager.getCache(ClassUtils.BOOKS_COLLECTION_NAME);
        Cache.ValueWrapper beforeFillingCache = bookCache.get(id);


        // when
        Optional<Book> bookFromRepo = bookService.findById(id);
        Optional<Book> bookFromCache = bookService.findById(id);

        // then
        Cache.ValueWrapper afterFillingCache = bookCache.get(id);
        assertNull(beforeFillingCache);
        assertNotNull(afterFillingCache);
        assertTrue(bookFromRepo.isPresent());
        assertTrue(bookFromCache.isPresent());
        assertEquals(bookFromCache, bookFromRepo);

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    @DirtiesContext
    public void shouldFindAllBooksFromCache() {

        // given
        when(repository.findAll()).thenReturn(
                Lists.newArrayList(sampleBook));

        // when
        List<Book> booksFromRepo = bookService.findAll();
        List<Book> booksFromCache = bookService.findAll();

        // then
        assertFalse(booksFromRepo.isEmpty());
        assertFalse(booksFromCache.isEmpty());
        assertEquals(booksFromCache, booksFromRepo);

        verify(repository, times(1)).findAll();

    }

    @Configuration
    @ComponentScan({"com.dcs.book.service", "com.dcs.book.model"})
    @EnableAutoConfiguration
    @EnableCaching
    public static class TestCacheConfig {
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(ClassUtils.BOOKS_COLLECTION_NAME);
        }
    }

}
