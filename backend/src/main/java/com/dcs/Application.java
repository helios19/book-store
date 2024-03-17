package com.dcs;

import com.dcs.book.model.Book;
import com.dcs.book.repository.BookRepository;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Main Spring Boot Application class. Note that a {@link CommandLineRunner} is created
 * to initialize the H2 database with a set of books.
 */
@EntityScan({"com.dcs.book.model"})
@EnableJpaRepositories({"com.dcs.book.repository"})
@SpringBootApplication
@Slf4j
public class Application {
    public static final void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Initializes the database with a set of book samples from local static file.
     *
     * @param bookRepository Book repository to save the rateExchanges
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner init(BookRepository bookRepository) {

        log.info("start initializing database...");

        try {
            return (evt) -> bookRepository.saveAll(

                    new BufferedReader(
                            new InputStreamReader(ResourceUtils.getURL("classpath:book_sample.csv").openStream()))
                            .lines()
                            .skip(1)
                            .filter(s -> !Strings.isNullOrEmpty(s) && s.split(",").length == 6)
                            .map(s -> {

                                String[] cols = s.split(",");

                                return Book
                                        .builder()
                                        .title(cols[1])
                                        .author(cols[2])
                                        .country(cols[3])
                                        .genre(cols[4])
                                        .year(cols[5])
                                        .build();
                                    })
                            .collect(Collectors.toList())
            );
        } finally {
            log.info("exchange rates loaded...");
        }
    }

}
