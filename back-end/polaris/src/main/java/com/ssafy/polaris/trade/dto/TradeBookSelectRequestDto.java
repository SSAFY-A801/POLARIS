package com.ssafy.polaris.trade.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TradeBookSelectRequestDto {
	private Long chatRoomId;
	private Long userId;
	List<BookDto> addedBooks;
	List<BookDto> deletedBooks;

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	public static class BookDto {
		private Long id;
		private String bookIsbn;
	}
}
