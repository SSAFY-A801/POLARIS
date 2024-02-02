package com.ssafy.polaris.book.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.user.domain.User;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.connectentity.PromotionUserBook;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class UserBook extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@NotNull
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_isbn")
	@NotNull
	private Book book;

	@Column(length = 600)
	@NotNull
	private String userBookDescription;

	@Column(name = "price")
	private Integer userBookPrice; // nullable한 경우 참조형 Wrapper class!

	@NotNull
	@Column(columnDefinition="tinyint(1)")
	private Boolean isOpened;

	@NotNull
	@Column(columnDefinition="tinyint(1)")
	private Boolean isOwned;

	@NotNull
	@Column(columnDefinition="VARCHAR(10)")
	@Enumerated(EnumType.STRING)
	private UserBookTradeType userBookTradeType;

	@OneToMany(mappedBy = "userBook")
	List<PromotionUserBook> promotionUserBooks = new ArrayList<>();

	public void deleteUserBook(LocalDateTime now) {
		setDeletedAt(now);
	}
}
