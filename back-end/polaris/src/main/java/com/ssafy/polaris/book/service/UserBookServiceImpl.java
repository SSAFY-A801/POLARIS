package com.ssafy.polaris.book.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.polaris.book.dto.*;
import com.ssafy.polaris.book.repository.BookRepository;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.series.dto.SeriesMapper;
import com.ssafy.polaris.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    public void createUserBook(Long userId, BookListRequestDto bookListRequestDto) {
        List<BookRequestDto> books = bookListRequestDto.getBooks();
        for(BookRequestDto b : books){
            bookRepository.save(userBookMapper.toBookEntity(b));
            userBookRepository.save(userBookMapper.toUserBookEntity(userId, b));
            if(b.getSeriesId() != null){
                seriesRepository.save(seriesMapper.toSeriesEntity(b.getSeriesId(), b.getSeriesName()));
            }
        }
    }

    @Override
    public List<UserBookResponseDto> getLibrary(Long userId) {
        return userBookRepository.findAllByUserId(userId);
    }

    @Override
    public UserBookResponseDto getUserBook(Long userId, String isbn){
        return userBookRepository.getUserBook(userId, isbn);
    }
}
