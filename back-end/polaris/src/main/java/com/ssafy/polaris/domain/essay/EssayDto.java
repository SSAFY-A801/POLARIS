package com.ssafy.polaris.domain.essay;

import java.time.LocalDateTime;

import com.ssafy.polaris.domain.book.UserBook;
import com.ssafy.polaris.domain.user.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class EssayDto {
	private int id;
	private User user;
	private UserBook userBook;
	private String title;
	private String content;
	private int hit;
	// private int scrapAmount;
	// private int repliesAmount;
	private String isOpened;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
