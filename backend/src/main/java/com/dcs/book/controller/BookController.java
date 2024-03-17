package com.dcs.book.controller;

import com.dcs.book.dto.BookDto;
import com.dcs.book.exception.BookNotFoundException;
import com.dcs.book.model.Book;
import com.dcs.book.service.BookService;
import com.dcs.common.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dcs.common.utils.ClassUtils.convertToDto;

/**
 * Book controller class defining the HTTP operations available for the {@link Book} resource. This controller
 * is covering all CRUD operations for {@link Book} resource.
 *
 * @see Book
 * @see BookService
 * @see RestController
 */
@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Save a given book.
     *
     * @return The whole book summary report
     * @throws BookNotFoundException if no book found in database
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> save(@Valid @RequestBody Book book) {
        log.info("saving book");

        return ResponseEntity
                .ok()
                .body(convertToDto(bookService.save(book)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookDto> update(@Valid @RequestBody Book newBook, @PathVariable Long id) {
        log.info("updating book");

        bookService.findById(Long.valueOf(id))
                .orElseThrow(BookNotFoundException::new);

        newBook.setId(id);

        return ResponseEntity
                .ok()
                .body(convertToDto(bookService.save(newBook)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Long id) {
        log.info("deleting book");

        Book book = bookService.findById(Long.valueOf(id))
                .orElseThrow(BookNotFoundException::new);

        bookService.delete(book);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> get(@PathVariable Long id) {
        log.info("getting book");

        Book book = bookService.findById(Long.valueOf(id))
                .orElseThrow(BookNotFoundException::new);

        return ResponseEntity
                .ok()
                .body(convertToDto(book));
    }

    /**
     * Returns all books.
     *
     * @return The list of books
     * @throws BookNotFoundException if no book found in database
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(defaultValue = "id,desc") String[] sort) {
        log.info("loading all books");

        List<Sort.Order> orders = getOrders(sort);

//        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        List<Book> books = bookService.findAll(Sort.by(orders));

        if (CollectionUtils.isEmpty(books)) {
            throw new BookNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(books.stream()
                        .map(ClassUtils::convertToDto)
                        .collect(Collectors.toList()));
    }

    private List<Sort.Order> getOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
