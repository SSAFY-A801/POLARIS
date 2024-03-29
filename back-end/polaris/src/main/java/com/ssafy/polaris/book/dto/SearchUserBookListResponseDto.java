package com.ssafy.polaris.book.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserBookListResponseDto {
	List<SearchUserBookResponseDto> searchBooks;
}
