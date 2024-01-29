package com.ssafy.polaris.user.repository;

import com.ssafy.polaris.profile.dto.ProfileDto;
import com.ssafy.polaris.trade.domain.TradeStatus;
import com.ssafy.polaris.trade.domain.TradeType;
import com.ssafy.polaris.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//	public User findUserById(Long userId);

	public User getReferenceById(long userId);

	@Query("select count(u) from User u inner join Trade t on u.id = t.sender.id " +
			"where t.finishedAt is not null " +
			"and t.sender.id = :userId " +
			"and t.status = :tradeStatus " +
			"and t.tradeType = :tradeType")
	int getTradeCnt(@Param("userId") Long userId,
					@Param("tradeStatus") TradeStatus tradeStatus,
					@Param("tradeType") TradeType tradeType);

	@Query("select u.nickname, u.profileUrl, u.introduction from User u " +
			"where u.id = :userId")
	ProfileDto getProfile(@Param("userId") Long userId);
}
