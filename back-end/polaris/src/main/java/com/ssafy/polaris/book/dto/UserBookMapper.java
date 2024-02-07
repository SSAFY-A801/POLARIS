package com.ssafy.polaris.book.dto;

import org.springframework.stereotype.Component;

import com.ssafy.polaris.book.domain.Book;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.book.repository.BookRepository;
import com.ssafy.polaris.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserBookMapper {

	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	public Book toBookEntity(BookRequestDto dto) {
		System.out.println("user book mapper mapper to entity");
		return Book.builder()
			.isbn(dto.getIsbn())
			.cover(dto.getCover())
			.title(dto.getTitle())
			.author(dto.getAuthor())
			.publisher(dto.getPublisher())
			.pubDate(dto.getPubDate())
			.seriesId(dto.getSeriesId())
			.priceStandard(dto.getPriceStandard())
			.bookDescription(dto.getBookDescription())
			.build();
	}

	public UserBook toUserBookEntity(Long userId, BookRequestDto dto) {
		return UserBook.builder()
			.user(userRepository.getReferenceById(userId))
			.book(bookRepository.getReferenceById(dto.getIsbn()))
			.userBookDescription("")
			.isOpened(dto.isOpened)
			.isOwned(dto.isOwned)
			.userBookTradeType(UserBookTradeType.UNDEFINED)
			.build();
	}
}
