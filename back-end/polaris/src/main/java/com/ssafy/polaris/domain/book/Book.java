package com.ssafy.polaris.domain.book;

import java.time.LocalDateTime;

import com.ssafy.polaris.domain.series.Series;

import jakarta.annotation.Nullable;
import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {
	@Id
	private String ISBN;

	private String title;
	private String description;
	private LocalDateTime pub_date;
	private String cover;
	private String publisher;
	private String author;
	private Integer price_standard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "series_id")
	@Nullable
	private Series series;

	public Book(String ISBN, String title, String description, LocalDateTime pub_date, String cover, String publisher,
		String author, Integer price_standard, @Nullable Series series) {
		this.ISBN = ISBN;
		this.title = title;
		this.description = description;
		this.pub_date = pub_date;
		this.cover = cover;
		this.publisher = publisher;
		this.author = author;
		this.price_standard = price_standard;
		this.series = series;
	}
}
