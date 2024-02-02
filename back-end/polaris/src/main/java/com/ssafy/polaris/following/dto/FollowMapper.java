package com.ssafy.polaris.following.dto;
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
