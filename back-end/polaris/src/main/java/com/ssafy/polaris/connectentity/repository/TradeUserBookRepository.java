package com.ssafy.polaris.connectentity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.polaris.connectentity.domain.TradeUserBook;

public interface TradeUserBookRepository extends JpaRepository<TradeUserBook, Long> {

	@Modifying
	@Query(value =
		"delete from TradeUserBook " +
			"where trade.id = :chatRoomId and userBook.id = :bookId")
	void deleteTradeUserBook(@Param("chatRoomId") Long chatRoomId, @Param("bookId") Long bookId);

	@Modifying
	@Query(value =
		"insert into TradeUserBook (trade, userBook) select t, ub from Trade t, UserBook ub where t.id = :chatRoomId and ub.id = :bookId")
	void addTradeUserBook(@Param("chatRoomId") Long chatRoomId, @Param("bookId") Long bookId);
}
