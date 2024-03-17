package com.dcs.common.utils;

//import com.dcs.ratexchg.dto.RateExchangeDto;
//import com.dcs.ratexchg.model.RateExchange;
import com.dcs.book.dto.BookDto;
import com.dcs.book.model.Book;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Utils class providing convenient factory and helper methods for {@link Book} resources.
 */
public class ClassUtils {
    public static final String BOOKS_COLLECTION_NAME = "books";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = ofPattern("d/MM/yyyy h:mm:ss a");
    public static final DateTimeFormatter DATE_FORMATTER = ofPattern(DATE_FORMAT_PATTERN);
    public static final int DEFAULT_PAGE_SIZE = 50;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();
    public static BigDecimal HUNDRED = new BigDecimal("100");
    public static BigDecimal TEN_MILLIONS = new BigDecimal("10000000");
    public static final String DEFAULT_COUNTRY = "United States";

    private ClassUtils() {
    }

    /**
     * Converts {@code isoDate} argument to {@link Date}.
     *
     * @param isoDate character sequence to convert
     * @return Date instance
     */
    public static Date toDate(String isoDate) {
        return Date.from(
                LocalDate.parse(isoDate, DATE_FORMATTER)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant());
    }

    /**
     * Converts {@link Date} argument to {@code isoDate}.
     *
     * @param date Date to format
     * @return Date instance
     */
    public static String fromDate(Date date) {
        return DATE_FORMATTER.format(LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    /**
     * Converts a {@link Book} instance into a {@link BookDto} object.
     *
     * @param book Book to convert
     * @return BookDto
     */
    public static BookDto convertToDto(Book book) {

        MODEL_MAPPER.addConverter(new AbstractConverter<Date, String>() {
                                      @Override
                                      protected String convert(Date source) {
                                         return ClassUtils.fromDate(source);
                                      }});
        MODEL_MAPPER.addConverter(new AbstractConverter<BigDecimal, String>() {
            @Override
            protected String convert(BigDecimal source) {
                return source .setScale(2, RoundingMode.CEILING).toString();
            }});

        return MODEL_MAPPER.map(book, BookDto.class);
    }

    /**
     * Converts a {@link BookDto} instance into a {@link Book} object.
     *
     * @param bookDto BookDto to convert
     * @return Book
     */
    public static Book convertToEntity(BookDto bookDto) {

        MODEL_MAPPER.addConverter(new AbstractConverter<Date, String>() {
            @Override
            protected String convert(Date source) {
                return ClassUtils.fromDate(source);
            }});
        MODEL_MAPPER.addConverter(new AbstractConverter<BigDecimal, String>() {
            @Override
            protected String convert(BigDecimal source) {
                return source .setScale(2, RoundingMode.CEILING).toString();
            }});

        return MODEL_MAPPER.map(bookDto, Book.class);
    }

//    /**
//     * Converts a {@link RateExchange} instance into a {@link RateExchangeDto} object.
//     *
//     * @param rateExchange RateExchange to convert
//     * @return RateExchangeDto
//     */
//    public static RateExchangeDto convertToDto(RateExchange rateExchange) {
//        return MODEL_MAPPER.map(rateExchange, RateExchangeDto.class);
//    }
}
