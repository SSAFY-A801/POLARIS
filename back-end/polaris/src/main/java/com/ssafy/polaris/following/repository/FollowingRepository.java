package com.ssafy.polaris.following.repository;

import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.following.dto.FollowResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Follow, Long> {
//    @Query("select f from Follow f where f.follower =: userId")
//    FollowDto getFollowings(@Param("userId") Long userId);

    @Query("select count(f) from Follow f where f.follower.id = :userId")
    int getFollowingCnt(@Param("userId")Long userId);
    @Query("select new com.ssafy.polaris.following.dto.FollowResponseDto(f.following.id) from Follow f where f.follower.id = :userId")
    List<FollowResponseDto> getFollowingList(@Param("userId") Long userId);
}
