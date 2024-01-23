package com.ssafy.polaris.domain.book;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.connectentity.PromotionUserBook;
import com.ssafy.polaris.domain.user.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class UserBook extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_isbn")
	private Book book;

	private int status;

	private String userBookDescription;

	@Nullable
	private Integer userBookPrice; // nullable한 경우 참조형 Wrapper class!

	private String isOpened;

	private String isOwned;

	private String tradeType;

	@OneToMany(mappedBy = "userBook")
	List<PromotionUserBook> promotionUserBooks = new ArrayList<>();

	public UserBook(User user, Book book, int status, String userBookDescription, @Nullable Integer userBookPrice,
		String isOpened, String isOwned, String tradeType, List<PromotionUserBook> promotionUserBooks) {
		this.user = user;
		this.book = book;
		this.status = status;
		this.userBookDescription = userBookDescription;
		this.userBookPrice = userBookPrice;
		this.isOpened = isOpened;
		this.isOwned = isOwned;
		this.tradeType = tradeType;
		this.promotionUserBooks = promotionUserBooks;
	}

	public void deleteUserBook(LocalDateTime now) {
		setDeletedAt(now);
	}
}
