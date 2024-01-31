package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookMapper;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.repository.BookRepository;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.book.response.DefaultResponse;
import com.ssafy.polaris.book.response.StatusCode;
import com.ssafy.polaris.series.dto.SeriesMapper;
import com.ssafy.polaris.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService{
    private final UserBookMapper userBookMapper;
    private final SeriesMapper seriesMapper;

    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;
    private final SeriesRepository seriesRepository;
    @Override
    public ResponseEntity<DefaultResponse<String>> createUserBook(Long userId, BookRequestDto bookRequestDto) {
        bookRepository.save(userBookMapper.toBookEntity(bookRequestDto));
        userBookRepository.save(userBookMapper.toUserBookEntity(userId, bookRequestDto));
        if(bookRequestDto.getSeriesId() != null){
            seriesRepository.save(seriesMapper.toSeriesEntity(bookRequestDto.getSeriesId(), bookRequestDto.getSeriesName()));
        }

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_CREATE_USER_BOOK, "");
    }

    @Override
    public ResponseEntity<DefaultResponse<UserBookListResponseDto>> getLibrary(Long userId) {
        List<UserBookResponseDto> userBooks = userBookRepository.findAllByUserId(userId);
        System.out.println("user id : " + userId);
        System.out.println(userBooks.toString());
        if(userBooks.isEmpty()){
            return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.FAIL_LIBRARY_VIEW, null);
        }
        UserBookListResponseDto userBookListResponseDto = new UserBookListResponseDto(userBooks);
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_LIBRARY_VIEW, userBookListResponseDto);
    }
}
