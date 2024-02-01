package com.ssafy.polaris.essay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Builder
public class EssayResponseDto {
	private long id;
	// private User user;
	// private UserBook userBook;
	private Long userBookId;
	private String title;
	private String content;
	// private int hit;
	private String isOpened;

	// private LocalDateTime createdAt;
	// private LocalDateTime updatedAt;
	// private LocalDateTime deletedAt;
}
