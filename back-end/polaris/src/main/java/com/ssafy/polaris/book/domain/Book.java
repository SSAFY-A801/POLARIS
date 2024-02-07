package com.ssafy.polaris.book.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book {
	@Id
	@Column(length = 13)
	private String isbn;

	@NotNull
	private String title;

	@Column(length = 30000)
	@NotNull
	private String bookDescription;

	@NotNull
	private LocalDateTime pubDate;

	@Column(length = 2048)
	private String cover;

	@NotNull
	private String publisher;

	@NotNull
	private String author;

	private Integer priceStandard;

	private Long seriesId;
}
