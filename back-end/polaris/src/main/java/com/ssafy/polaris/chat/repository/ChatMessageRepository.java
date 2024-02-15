package com.ssafy.polaris.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.polaris.chat.domain.ChatMessage;
import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

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

}
