package com.ssafy.polaris.essay.dto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.ssafy.polaris.book.domain.Book;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class EssayResponseDto {
	private long id;
	private String title;
	private String content;
	private int hit;
	private String isOpened;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
	// user
	private long userId;
	private String nickname;
	private String email;
	private String profileUrl;
	// book
	private long userBookId;
	private String bookCoverUrl;
	private String bookTitle;
	private String bookAuthor;

	public EssayResponseDto(Essay essay) {
		this.id = essay.getId();
		this.title = essay.getTitle();
		this.content = essay.getTitle();
		this.hit = essay.getHit();
		this.isOpened = essay.getIsOpened();
		this.createdAt = essay.getCreatedAt();
		this.updatedAt = essay.getUpdatedAt();
		this.deletedAt = essay.getDeletedAt();
		// TODO : 없는 값으로 만들 수 없도록 만들면 null 일 수 없다
		if (essay.getUserBook() != null) {
			UserBook userBook = essay.getUserBook();
			Book book = userBook.getBook();
			this.userBookId = userBook.getId();
			this.bookCoverUrl = book.getCover();
			this.bookTitle = book.getTitle();
			this.bookAuthor = book.getAuthor();
		}
		if (essay.getUser() != null) {
			User user = essay.getUser();
			this.userId = user.getId();
			this.nickname = user.getNickname();
			this.email = user.getEmail();
			this.profileUrl = user.getProfileUrl();
		}
	}
}
