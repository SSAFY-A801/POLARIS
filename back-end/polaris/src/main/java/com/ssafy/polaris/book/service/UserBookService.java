package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.BookListRequestDto;
import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.response.DefaultResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserBookService {
    void createUserBook(Long userId, BookListRequestDto bookListRequestDto);
    List<UserBookResponseDto> getLibrary(Long userId);
    UserBookResponseDto getUserBook(Long userId, String isbn);
}


