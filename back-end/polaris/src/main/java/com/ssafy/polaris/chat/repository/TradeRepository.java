package com.ssafy.polaris.chat.repository;

import java.util.List;

import com.ssafy.polaris.trade.dto.ExchangeHistoryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.polaris.chat.dto.BasicChatRoomResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomParticipantsResponseDto;
import com.ssafy.polaris.trade.domain.Trade;
import com.ssafy.polaris.trade.domain.TradeStatus;

public interface TradeRepository extends JpaRepository<Trade, Long> {
	// 나의 채팅방 목록
	@Query(value =
		"SELECT " +
			"    new com.ssafy.polaris.chat.dto.BasicChatRoomResponseDto( " +
			"        t.id as chatRoomId, " +
			"        CASE WHEN t.sender.id = :senderId THEN ru.id ELSE su.id END as receiverId, " +
			"        CASE WHEN t.sender.id = :senderId THEN ru.nickname ELSE su.nickname END as nickname, " +
			"        CASE WHEN t.sender.id = :senderId THEN ru.profileUrl ELSE su.profileUrl END as profileUrl, " +
			"        t.tradeType as tradeType, " +
			"        t.status as tradeStatus ) " +
			"FROM " +
			"    Trade t " +
			"    LEFT JOIN User ru ON t.receiver.id = ru.id AND t.sender.id = :senderId " +
			"    LEFT JOIN User su ON t.sender.id = su.id AND t.receiver.id = :senderId " +
			"WHERE " +
			"    t.sender.id = :senderId OR t.receiver.id = :senderId")
	List<BasicChatRoomResponseDto> getChatRoomList(@Param("senderId") Long senderId);

	@Modifying
	@Query(value =
		"update Trade " +
			"set finishedAt = CURRENT_TIMESTAMP, " +
			"status = :tradeStatus " +
			"where id = :chatRoomId"
	)
	void completeTrade(@Param("chatRoomId") Long chatRoomId, @Param("tradeStatus") TradeStatus tradeStatus);

	@Query( value =
	"SELECT " +
		"new com.ssafy.polaris.chat.dto.ChatRoomParticipantsResponseDto( " +
		"t.id AS chatRoomId, "+
		":userId AS senderId, "+
		"u.id AS receiverId, "+
		"u.nickname AS receiverNickname, "+
		"u.profileUrl AS receiverProfileUrl ) "+
		"FROM Trade t "+
		"JOIN User u ON t.sender.id = u.id "+
		"WHERE t.id = :chatRoomId AND t.receiver.id = :userId "+

		"UNION "+

		"SELECT " +
		"new com.ssafy.polaris.chat.dto.ChatRoomParticipantsResponseDto( " +
		"t.id AS chatRoomId, "+
		":userId AS senderId, "+
		"u.id AS receiverId, "+
		"u.nickname AS receiverNickname, "+
		"u.profileUrl AS receiverProfileUrl ) "+
		"FROM Trade t "+
		"JOIN User u ON t.receiver.id = u.id "+
		"WHERE t.id = :chatRoomId AND t.sender.id = :userId "
	)
	ChatRoomParticipantsResponseDto getChatRoomParticipants(@Param(value = "chatRoomId") Long chatRoomId, @Param(value = "userId") Long userId);

	// @Query(value =
	// 	"select new com.ssafy.polaris.chat.dto.BasicChatRoomResponseDto( " +
	// 		"    coalesce(case when t.sender.id = :senderId then ru.id end, case when t.receiver.id = :senderId then su.id end) as receiverId, " +
	// 		"    coalesce(case when t.sender.id = :senderId then ru.nickname end, case when t.receiver.id = :senderId then su.nickname end) as nickname, " +
	// 		"    coalesce(case when t.sender.id = :senderId then ru.profileUrl end, case when t.receiver.id = :senderId then su.profileUrl end) as profileUrl, " +
	// 		"    t.tradeType as tradeType, " +
	// 		"    t.status as tradeStatus) " +
	// 		"from " +
	// 		"    Trade t " +
	// 		"        left join " +
	// 		"    User ru on t.receiver.id = ru.id and t.sender.id = :senderId " +
	// 		"        left join " +
	// 		"    User su on t.sender.id = su.id and t.receiver.id = :senderId " +
	// 		"where " +
	// 		"    t.sender.id = :senderId or t.receiver.id = :senderId")
	// List<BasicChatRoomResponseDto> getChatRoomList(@Param("senderId") Long senderId);

	@Query("select new com.ssafy.polaris.trade.dto.ExchangeHistoryResponseDto( " +
			" t.id, u.id, u.nickname, ub.id, b.title, t.finishedAt)" +
			"from Trade t " +
			"	inner join TradeUserBook tub on t.id = tub.trade.id " +
			"	inner join User u on (t.sender.id = u.id or t.receiver.id = u.id) " +
			"	inner join UserBook ub on (u.id = ub.user.id and tub.userBook.id = ub.id)" +
			"	inner join Book b on ub.book.isbn = b.isbn " +
			"where (t.receiver.id = :userId or t.sender.id = :userId) " +
			"	and t.tradeType = 'EXCHANGE' " +
			"	and t.status = 'COMPLETED' " +
			"order by t.id, u.id ")
	List<ExchangeHistoryResponseDto> getExchangeHistory(@Param("userId") Long userId);





}
