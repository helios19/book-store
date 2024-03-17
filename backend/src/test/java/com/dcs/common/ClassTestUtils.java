package com.dcs.common;

import com.dcs.book.model.Book;

public class ClassTestUtils {

    public static final Book BOOK_SAMPLE = Book
            .builder()
            .id(1L)
            .title("book 1")
            .author("Charles Dickens")
            .country("United States")
            .genre("Science Fiction")
            .year("1880")
//            .creationDate(ClassUtils.toDate("2023-09-10"))
//            .amount(new BigDecimal("000092500000000").divide(ClassUtils.TEN_MILLIONS))
            .build();

    public static final Book BOOK_SAMPLE_2 = Book
            .builder()
            .id(2L)
            .title("book 2")
            .author("Charles Dickens")
            .country("United States")
            .genre("Science Fiction")
            .year("1880")
//            .creationDate(ClassUtils.toDate("2023-09-11"))
//            .amount(new BigDecimal("000099800000000").divide(ClassUtils.TEN_MILLIONS))
            .build();

    public static final Book BOOK_SAMPLE_3 = Book
            .builder()
            .id(2L)
            .title("book 3")
            .author("Charles Dickens")
            .country("United States")
            .genre("Science Fiction")
            .year("1880")
//            .creationDate(ClassUtils.toDate("2010-09-11"))
//            .amount(new BigDecimal("000099800000000").divide(ClassUtils.TEN_MILLIONS))
            .build();

}
