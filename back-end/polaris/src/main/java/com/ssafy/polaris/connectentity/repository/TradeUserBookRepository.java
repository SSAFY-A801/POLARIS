package com.ssafy.polaris.connectentity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.polaris.chat.dto.ChatRoomTradeBookResponseDto;
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

	@Query( value =
	"select "+
		"new com.ssafy.polaris.chat.dto.ChatRoomTradeBookResponseDto ( "+
		"ub.user.id as userId, " +
		"ub.id as id, "+
		"b.title as title, "+
		"b.author as author, "+
		"ub.userBookTradeType as status, "+
		"ub.userBookPrice as price, "+
		"b.seriesId as seriesId ) " +
	"from TradeUserBook tub "+
		"join UserBook ub on tub.userBook.id = ub.id "+
		"join Book b on ub.book.isbn = b.isbn "+
		"join User u on u.id = ub.user.id "+
	"where tub.trade.id = :chatRoomId "
	)
	List<ChatRoomTradeBookResponseDto> getChatRoomTradeBookList(@Param("chatRoomId") Long chatRoomId);

	 @Modifying
	 @Query(value =
	 "update UserBook ub "+
			 "set ub.isOwned = false, "+
			 "ub.userBookTradeType = 'UNDEFINED' "+
			 "where ub.id in ( " +
			 "    select tub.userBook.id " +
			 "    from TradeUserBook tub " +
			 "    where tub.trade.id = :chatRoomId) "
	 )
	 void completeTradeUserBooks(@Param("chatRoomId") Long chatRoomId);
}
