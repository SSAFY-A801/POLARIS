package com.ssafy.polaris.promotion.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.ssafy.polaris.global.BaseEntity;
import com.ssafy.polaris.connectentity.domain.PromotionUserBook;
import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
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
@SQLDelete(sql = "update promotion set deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
public class Promotion extends BaseEntity {
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", updatable = false, insertable = false)
	private User user;

	@Column(name = "user_id")
	private Long userId;

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

	public void updateHit() {
		hit += 1;
	}

	public void update(PromotionRequestDto promotionRequestDto) {
		this.title = promotionRequestDto.getTitle();
		this.content = promotionRequestDto.getContent();
		this.promotionUserBooks.clear();
		for (Long userBookIds : promotionRequestDto.getUserBookIds()) {
			promotionUserBooks.add(new PromotionUserBook(userBookIds, promotionRequestDto.getId()));
		}
	}

	/*
	 * hit은 기본을 0으로 하기 때문에 넣어주지 않는다.
	 */
}
