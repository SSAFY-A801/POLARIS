package com.ssafy.polaris.book;

import java.time.LocalDateTime;

import com.ssafy.polaris.series.Series;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id @Column(length = 13)
	private String ISBN;

	@NotNull
	private String title;

	@Column(length = 30000)
	@NotNull
	private String description;

	@NotNull
	private LocalDateTime pub_date;

	@Column(length = 2048)
	private String cover;

	@NotNull
	private String publisher;

	@NotNull
	private String author;

	private Integer price_standard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "series_id")
	private Series series;
}
