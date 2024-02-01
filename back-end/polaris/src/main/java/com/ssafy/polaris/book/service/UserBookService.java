package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.response.DefaultResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserBookService {
    String createUserBook(Long userId, BookRequestDto bookRequestDto);
    List<UserBookResponseDto> getLibrary(Long userId);}
