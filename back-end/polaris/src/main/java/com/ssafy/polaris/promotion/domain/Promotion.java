package com.ssafy.polaris.promotion.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.ssafy.polaris.global.BaseEntity;
import com.ssafy.polaris.connectentity.domain.PromotionUserBook;
import com.ssafy.polaris.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class Promotion extends BaseEntity {
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "promotion")
	private List<PromotionUserBook> promotionUserBooks = new ArrayList<>();

	@NotNull
	private String title;

	@NotNull
	@Column(length = 30000)
	private String content;

	@NotNull
	@ColumnDefault(value = "0")
	private int hit;
	// private int favoritesAmount;

	public void updateHit() {
		hit += 1;
	}

	/*
	 * hit은 기본을 0으로 하기 때문에 넣어주지 않는다.
	 */

	// public void increaseFavoritesAmount() {
	// 	favoritesAmount += 1;
	// }
	//
	// public void decreaseFavoritesAmount() {
	// 	if (favoritesAmount > 0)
	// 		favoritesAmount -= 1;
	// }
}
