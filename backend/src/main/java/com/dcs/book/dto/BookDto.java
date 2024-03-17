package com.dcs.book.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Book DTO class representing a book.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String country;
    private String genre;
    private String year;
    private Boolean borrowed;
}


