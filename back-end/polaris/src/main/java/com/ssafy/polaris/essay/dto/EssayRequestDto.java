package com.ssafy.polaris.essay.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class EssayRequestDto {
	private long id;
	private long userBookId;
	private String title;
	private String content;
	private Boolean isOpened;
}
