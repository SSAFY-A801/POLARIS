package com.ssafy.polaris.book.dto;

import com.ssafy.polaris.book.domain.Book;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.book.repository.BookRepository;
import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowMapper {

    private final UserRepository userRepository;

    public Follow toFollowEntity(Long userId, Long followingId){
        return Follow.builder()
                .follower(userRepository.getReferenceById(userId))
                .following(userRepository.getReferenceById(followingId))
                .build();
    }
}
