package com.ssafy.polaris.promotion.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PromotionListResponseDto {
	private Long userId;
	private String profileUrl;
	private String nickname;
	private String region;
	private Long promotionId;
	private String title;
	private String content;
	private Long totalPrice;
	private Boolean isFavorite;
	private Long favoriteCnt;

	public PromotionListResponseDto(Long userId, String profileUrl, String nickname, String region, Long promotionId,
		String title, String content, Long totalPrice, Boolean isFavorite, Long favoriteCnt) {
		this.userId = userId;
		this.profileUrl = profileUrl;
		this.nickname = nickname;
		this.region = region;
		this.promotionId = promotionId;
		this.title = title;
		this.content = content;
		this.totalPrice = totalPrice;
		this.isFavorite = isFavorite;
		this.favoriteCnt = favoriteCnt;
	}
}
