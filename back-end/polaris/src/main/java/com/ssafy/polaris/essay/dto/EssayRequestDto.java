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
	private long id;
	private long userBookId;
	private String title;
	private String content;
	private boolean isOpened;
}
