package com.ssafy.polaris.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.trade.domain.TradeStatus;
import com.ssafy.polaris.trade.domain.TradeType;
import com.ssafy.polaris.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByEmail(String email);

	Optional<User> findUserByNickname(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	boolean existsByOauth(String oauth);

	@Query("select count(u) from User u inner join Trade t on u.id = t.sender.id " +
		"where t.finishedAt is not null " +
		"and (t.sender.id = :userId or t.receiver.id = :userId) " +
		"and t.status = :tradeStatus " +
		"and t.tradeType = :tradeType")
	int getTradeCnt(@Param("userId") Long userId,
		@Param("tradeStatus") TradeStatus tradeStatus,
		@Param("tradeType") TradeType tradeType);

	Optional<User> findUserByOauth(String oauth);
}
