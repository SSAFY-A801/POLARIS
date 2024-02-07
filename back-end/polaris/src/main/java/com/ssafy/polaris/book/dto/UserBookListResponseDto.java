package com.ssafy.polaris.book.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBookListResponseDto {
	List<UserBookResponseDto> books;
}
