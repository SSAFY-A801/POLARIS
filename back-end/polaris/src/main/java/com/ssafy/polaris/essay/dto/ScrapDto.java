package com.ssafy.polaris.essay.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScrapDto {
	private Long id;
	private Long essayId;
	private Long userId;
	private Boolean isDeleted;
}
