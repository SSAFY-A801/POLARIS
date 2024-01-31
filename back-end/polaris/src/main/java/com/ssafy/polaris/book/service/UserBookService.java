package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.response.DefaultResponse;
import org.springframework.http.ResponseEntity;

public interface UserBookService {
    ResponseEntity<DefaultResponse<String>> createUserBook(Long userId, BookRequestDto bookRequestDto);
    ResponseEntity<DefaultResponse<UserBookListResponseDto>> getLibrary(Long userId);}
