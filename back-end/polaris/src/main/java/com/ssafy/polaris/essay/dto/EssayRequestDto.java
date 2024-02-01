package com.ssafy.polaris.essay.dto;

import java.time.LocalDateTime;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class EssayRequestDto {
	// private long id;
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
