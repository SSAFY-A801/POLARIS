package com.ssafy.polaris.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.polaris.chat.domain.ChatMessage;
import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

	// List<ChatMessage> findTop100ByTrade_IdOrderByCreatedAtAsc(Long chatRoomId);

	@Query(value =
	"SELECT "+
		"new com.ssafy.polaris.chat.dto.ChatMessageSaveDto ( " +
		"cm.type AS type, "+
		"t.id AS chatRoomId, "+
		"u.id AS userId, "+
		"u.nickname AS nickname, "+
		"cm.createdAt AS createdAt, "+
		"cm.message AS message ) "+
	"FROM ChatMessage cm "+
		"JOIN Trade t ON t.id = cm.trade.id "+
		"JOIN User u ON  u.id = cm.user.id "+
		"WHERE t.id = :chatRoomId "+
		"ORDER BY cm.createdAt"
	)
	List<ChatMessageSaveDto> getTop100ChatMessages(@Param(value = "chatRoomId") Long chatRoomId);


	// @Query("select new com.ssafy.polaris.book.dto.SearchUserBookResponseDto(ub.id, ub.user.id, " +
	// 	" ub.user.nickname, ub.user.regcode, b.isbn, b.title," +
	// 	"b.author, b.cover, ub.userBookTradeType) " +
	// 	"from UserBook ub " +
	// 	"left join Book b on ub.book.isbn = b.isbn " +
	// 	"left join User u on u.id = ub.user.id ")
	// List<SearchUserBookResponseDto> searchAll();

}
