package com.ssafy.polaris.promotion.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PromotionRequestDto {
	private long id;
	private String title;
	private String content;
	private List<Long> userBookIds;
}
