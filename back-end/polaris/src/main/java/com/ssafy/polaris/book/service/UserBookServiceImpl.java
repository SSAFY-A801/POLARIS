package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookMapper;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.repository.BookRepository;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.book.response.DefaultResponse;
import com.ssafy.polaris.book.response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService{
    private final UserBookMapper userBookMapper;
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;

    @Override
    public String createUserBook(Long userId, BookRequestDto bookRequestDto) {
        bookRepository.save(userBookMapper.toBookEntity(bookRequestDto));
        userBookRepository.save(userBookMapper.toUserBookEntity(userId, bookRequestDto));

        return "";
    }

    @Override
    public List<UserBookResponseDto> getLibrary(Long userId) {
        return userBookRepository.findAllByUserId(userId);

    }
}
