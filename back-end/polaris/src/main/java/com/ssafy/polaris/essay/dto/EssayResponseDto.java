package com.ssafy.polaris.essay.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.polaris.book.domain.Book;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.comment.dto.CommentReponseDto;
import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.user.domain.User;

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
	private Boolean isOpened;
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
	// comment
	private List<CommentReponseDto> comments;

	public EssayResponseDto(Essay essay) {
		this.id = essay.getId();
		this.title = essay.getTitle();
		this.content = essay.getContent();
		this.hit = essay.getHit();
		this.isOpened = essay.getIsOpened();
		this.createdAt = essay.getCreatedAt();
		this.updatedAt = essay.getUpdatedAt();
		this.deletedAt = essay.getDeletedAt();
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
