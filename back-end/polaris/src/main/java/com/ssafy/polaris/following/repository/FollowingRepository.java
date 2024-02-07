package com.ssafy.polaris.following.repository;

import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.following.dto.FollowResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Follow, Long> {
	@Query("select count(f) from Follow f where f.follower.id = :userId")
	int getFollowingCnt(@Param("userId") Long userId);

	@Query(
		"select new com.ssafy.polaris.following.dto.FollowResponseDto(f.following.id, f.following.profileUrl, " +
			"f.following.nickname, f.following.regcode) from Follow f " +
			"where f.follower.id = :userId ")
	List<FollowResponseDto> getFollowingList(@Param("userId") Long userId);

	@Modifying
	@Query("delete from Follow f where f.following.id = :followingId and f.follower.id = :followerId")
	int deleteFollowUser(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

	@Query("select f from Follow f where f.follower.id = :followerId and " +
		"f.following.id = :followingId")
	Follow findRelation(@Param("followerId") Long followerId, @Param("followingId") Long followingId);
}
