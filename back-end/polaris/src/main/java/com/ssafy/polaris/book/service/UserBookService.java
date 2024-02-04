package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.*;

import java.util.List;

public interface UserBookService {
    void createUserBook(Long userId, BookListRequestDto bookListRequestDto);
    List<UserBookResponseDto> getLibrary(Long userId);
    UserBookResponseDto getUserBook(Long userId, String isbn);
    int updateUserBook(Long userId, UserBookUpdateRequestDto data);
}


