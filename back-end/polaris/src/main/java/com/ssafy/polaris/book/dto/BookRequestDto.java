package com.ssafy.polaris.book.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
	String isbn;
	String title;
	String bookDescription;
	LocalDateTime pubDate;
	String cover;
	String publisher;
	String author;
	Integer priceStandard;
	Long seriesId;
	String seriesName;
	Boolean isOpened;
	Boolean isOwned;
}
