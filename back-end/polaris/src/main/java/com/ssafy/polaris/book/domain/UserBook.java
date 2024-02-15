package com.ssafy.polaris.book.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.ssafy.polaris.book.dto.UserBookUpdateRequestDto;
import com.ssafy.polaris.global.BaseEntity;
import com.ssafy.polaris.user.domain.User;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user_book SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
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
	@Column(columnDefinition = "tinyint(1)")
	private Boolean isOpened;

	@NotNull
	@Column(columnDefinition = "tinyint(1)")
	private Boolean isOwned;

	@NotNull
	@Column(columnDefinition = "VARCHAR(10)")
	@Enumerated(EnumType.STRING)
	private UserBookTradeType userBookTradeType;

	// update method
	public void updateUserBook(UserBookUpdateRequestDto dto) {
		this.userBookDescription = dto.getUserBookDescription();
		this.userBookPrice = dto.getUserBookPrice();
		this.isOpened = dto.getIsOpened();
		this.isOwned = dto.getIsOwned();
		this.userBookTradeType = dto.getUserBookTradeType();
	}
}
