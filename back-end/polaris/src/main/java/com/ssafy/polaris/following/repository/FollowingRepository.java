package com.ssafy.polaris.following.repository;

import com.ssafy.polaris.following.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Follow, Long> {
}
