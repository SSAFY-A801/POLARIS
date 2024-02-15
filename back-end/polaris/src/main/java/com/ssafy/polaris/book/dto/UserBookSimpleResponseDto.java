package com.ssafy.polaris.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookSimpleResponseDto {
	Long id;
	String cover;
	String title;
	String author;
	String isbn;
	String publisher;
	String userBookDescription = "";
	Integer userBookPrice;
	Boolean isOpened;
	Boolean isOwned;
	UserBookTradeType userBookTradeType;

	public UserBookSimpleResponseDto(UserBook userBook) {
		this.id = userBook.getId();
		this.cover = userBook.getBook().getCover();
		this.title = userBook.getBook().getTitle();
		this.author = userBook.getBook().getAuthor();
		this.isbn = userBook.getBook().getIsbn();
		this.publisher = userBook.getBook().getPublisher();
		this.userBookDescription = userBook.getUserBookDescription();
		this.userBookPrice = userBook.getUserBookPrice();
		this.isOpened = userBook.getIsOpened();
		this.isOwned = userBook.getIsOwned();
		this.userBookTradeType = userBook.getUserBookTradeType();
	}
}
