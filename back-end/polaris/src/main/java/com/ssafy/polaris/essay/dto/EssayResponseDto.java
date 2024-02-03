package com.ssafy.polaris.essay.dto;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EssayResponseDto {
	private Long id;
	private User user;
	private Long userId;
	private UserBook userBook;
	private Long userBookId;
	private String title;
	private String content;
	private int hit;
	private String isOpened;

	// private LocalDateTime createdAt;
	// private LocalDateTime updatedAt;
	// private LocalDateTime deletedAt;
}
