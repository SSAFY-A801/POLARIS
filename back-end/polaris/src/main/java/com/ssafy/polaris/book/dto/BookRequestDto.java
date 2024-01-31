package com.ssafy.polaris.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
    String isbn;
    String title;
    String description;
    LocalDateTime pubDate;
    String cover;
    String publisher;
    String author;
    Integer priceStandard;
    Long seriesId;
    Boolean isOpened;
    Boolean isOwned;
}
