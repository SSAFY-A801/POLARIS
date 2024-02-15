package com.ssafy.polaris.book.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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