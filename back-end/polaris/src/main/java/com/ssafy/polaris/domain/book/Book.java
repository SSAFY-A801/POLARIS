package com.ssafy.polaris.domain.book;

import java.time.LocalDateTime;

import com.ssafy.polaris.domain.series.Series;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	private String title;

	@Column(length = 30000)
	private String description;

	private LocalDateTime pub_date;

	@Column(length = 2048)
	private String cover;


	private String publisher;

	private String author;

	private Integer price_standard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "series_id")
	@Nullable
	private Series series;
}
