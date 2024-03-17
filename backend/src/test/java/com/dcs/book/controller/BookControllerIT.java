package com.dcs.book.controller;

import com.dcs.Application;
import com.dcs.book.dto.BookDto;
import com.dcs.book.repository.BookRepository;
import com.dcs.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE;
import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE_2;
import static com.dcs.common.ClassTestUtils.BOOK_SAMPLE_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class})
@ContextConfiguration(name = "contextWithFakeBean")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUp() throws Exception {
        bookRepository.deleteAll();
        bookRepository.saveAll(Arrays.asList(BOOK_SAMPLE, BOOK_SAMPLE_3));
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    void shouldGetABook() {

        // Given
        Long idToGet = bookService.findAll().get(0).getId();
        String url = "http://localhost:" + port + "/books/" + idToGet;

        // When
        ResponseEntity<BookDto> response = testRestTemplate.getForEntity(url, BookDto.class);
        BookDto bookDto = response.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(bookDto);
    }


    @Test
    @Order(2)
    void shouldReturnAllBooks() {

        // Given
        String url = "http://localhost:" + port + "/books/?sort=title,asc";

        // When
        ResponseEntity<List> response = testRestTemplate.getForEntity(url, List.class);

        List<LinkedHashMap> bookDtos = (List<LinkedHashMap>) response.getBody();

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(bookDtos.size(), 2);
        assertEquals(bookDtos.get(0).get("title"), "book 1");
        assertEquals(bookDtos.get(0).get("country"), "United States");
        assertEquals(bookDtos.get(1).get("title"), "book 3");
        assertEquals(bookDtos.get(1).get("country"), "United States");
    }

    @Test
    @Order(3)
    public void shouldUpdateBook() throws JsonProcessingException {
        // Given
        Long idToUpdate = bookService.findAll().get(0).getId();
        String url = "http://localhost:" + port + "/books/" + idToUpdate;

        // When
        ResponseEntity<BookDto> response = testRestTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(BOOK_SAMPLE_2), BookDto.class);
        BookDto bookDto = response.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(bookDto.getTitle(), "book 2");
        assertEquals(bookDto.getCountry(), "United States");
        assertEquals(bookDto.getAuthor(), "Charles Dickens");
        assertEquals(bookDto.getGenre(), "Science Fiction");
    }

    @Test
    @Order(4)
    public void shouldAddBook() {
        // Given
        String url = "http://localhost:" + port + "/books/";

        // When
        ResponseEntity<BookDto> response = testRestTemplate.postForEntity(url, BOOK_SAMPLE_2, BookDto.class);
        BookDto bookDto = response.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(bookDto.getTitle(), "book 2");
        assertEquals(bookDto.getCountry(), "United States");
        assertEquals(bookDto.getAuthor(), "Charles Dickens");
        assertEquals(bookDto.getGenre(), "Science Fiction");
    }

    @Test
    @Order(5)
    public void shouldDeleteBook() {

        // Given
        Long idToDelete = bookService.findAll().get(0).getId();
        String url = "http://localhost:" + port + "/books/" + idToDelete;

        // When
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}