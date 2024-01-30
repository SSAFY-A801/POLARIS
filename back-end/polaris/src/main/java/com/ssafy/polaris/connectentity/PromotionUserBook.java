package com.ssafy.polaris.connectentity;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.promotion.Promotion;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PromotionUserBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_book_id")
	private UserBook userBook;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion_id")
	private Promotion promotion;

	public PromotionUserBook(UserBook userBook, Promotion promotion) {
		this.userBook = userBook;
		this.promotion = promotion;
	}
}
