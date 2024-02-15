package com.ssafy.polaris.book.dto;

import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.regcode.domain.Regcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserBookResponseDto {
	Long id;
	Long userId;
	String nickname;
	String profileUrl;
	Regcode regcode;
	String isbn;
	String title;
	String author;
	String cover;
	UserBookTradeType userBookTradeType;
}
