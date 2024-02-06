package com.ssafy.polaris.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyBooksDto {
    String isbn;
    String cover;
    String title;
    String author;
    String publisher;
    LocalDateTime pubDate;
    String bookDescription;
    Integer priceStandard;
}