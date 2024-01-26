package com.ssafy.polaris.domain.book;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.connectentity.PromotionUserBook;
import com.ssafy.polaris.domain.user.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBook extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_isbn")
	private Book book;

	@Column(length = 600)
	private String description;

	@Nullable
	private Integer userBookPrice; // nullable한 경우 참조형 Wrapper class!

	@Column(columnDefinition="tinyint(1)")
	private Boolean isOpened;

	@Column(columnDefinition="tinyint(1)")
	private Boolean isOwned;

	@Column(columnDefinition="CHAR(6)")
	private UserBookTradeType tradeType;

	@OneToMany(mappedBy = "userBook")
	List<PromotionUserBook> promotionUserBooks = new ArrayList<>();

	public void deleteUserBook(LocalDateTime now) {
		setDeletedAt(now);
	}
}
