package com.ssafy.polaris.domain.promotion;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.connectentity.PromotionUserBook;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Column;
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
public class Promotion extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "promotion")
	private List<PromotionUserBook> promotionUserBooks = new ArrayList<>();

	private String title;

	@Column(length = 30000)
	private String content;

	private int hit;

	// private int favoritesAmount;

	public void updateHit(){
		hit += 1;
	}

	/*
	* hit은 기본을 0으로 하기 때문에 넣어주지 않는다.
	 */
	public Promotion(User user, List<PromotionUserBook> promotionUserBooks, String title, String content) {
		this.user = user;
		this.promotionUserBooks = promotionUserBooks;
		this.title = title;
		this.content = content;
	}

	// public void increaseFavoritesAmount() {
	// 	favoritesAmount += 1;
	// }
	//
	// public void decreaseFavoritesAmount() {
	// 	if (favoritesAmount > 0)
	// 		favoritesAmount -= 1;
	// }
}
