package com.ssafy.polaris.book.service;

import java.util.List;

import com.ssafy.polaris.book.dto.BookListRequestDto;
import com.ssafy.polaris.book.dto.SearchUserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookListDeleteRequestDto;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.dto.UserBookUpdateRequestDto;
import com.ssafy.polaris.book.dto.WeeklyBooksDto;

public interface UserBookService {
	void createUserBook(Long userId, BookListRequestDto bookListRequestDto);

	List<UserBookResponseDto> getLibrary(Long userId);

	UserBookResponseDto getUserBook(Long userId, String isbn);

	int updateUserBook(Long userId, UserBookUpdateRequestDto data);

	int deleteUserBook(Long userId, UserBookListDeleteRequestDto data);

	SearchUserBookListResponseDto searchByConditionUserBook(Long regcode, String queryType, String keyword);

	public void saveWeeklyBooks();

	List<WeeklyBooksDto> getCachedWeeklyBooks();
}


