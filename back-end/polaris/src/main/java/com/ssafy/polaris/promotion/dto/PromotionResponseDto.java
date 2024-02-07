package com.ssafy.polaris.promotion.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.polaris.book.dto.UserBookSimpleResponseDto;
import com.ssafy.polaris.connectentity.domain.PromotionUserBook;
import com.ssafy.polaris.promotion.domain.Promotion;
import com.ssafy.polaris.user.dto.UserResponseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PromotionResponseDto {
	private Long id;
	private Integer hit;
	private String title;
	private String content;
	private UserResponseDto user;
	private List<UserBookSimpleResponseDto> userBooks;

	public PromotionResponseDto(Promotion promotion) {
		this.id = promotion.getId();
		this.hit = promotion.getHit();
		this.title = promotion.getTitle();
		this.content = promotion.getContent();
		this.user = new UserResponseDto(promotion.getUser());
		this.userBooks = promotion.getPromotionUserBooks().stream()
			.map(PromotionUserBook::getUserBook)
			.map(UserBookSimpleResponseDto::new)
			.collect(Collectors.toList());
	}
}
