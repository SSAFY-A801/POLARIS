package com.ssafy.polaris.book.dto;

import com.ssafy.polaris.book.domain.UserBookTradeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookUpdateRequestDto {
	String isbn;
	String userBookDescription;
	Integer userBookPrice;
	Boolean isOpened;
	Boolean isOwned;
	UserBookTradeType userBookTradeType;
}
