package com.ssafy.polaris.following.repository;

import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.following.dto.FollowDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowingRepository extends JpaRepository<Follow, Long> {
//    @Query("select f from Follow f where f.follower =: userId")
//    FollowDto getFollowings(@Param("userId") Long userId);
}
