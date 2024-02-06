package com.ssafy.polaris.connectentity.domain;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.promotion.domain.Promotion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionUserBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_book_id", updatable = false, insertable = false)
	private UserBook userBook;

	@Column(name = "user_book_id")
	private Long userBookId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion_id", updatable = false, insertable = false)
	private Promotion promotion;

	@Column(name = "promotion_id")
	private Long promotionId;

	public PromotionUserBook(Long userBookId, Long promotionId) {
		this.userBookId = userBookId;
		this.promotionId = promotionId;
	}
}
