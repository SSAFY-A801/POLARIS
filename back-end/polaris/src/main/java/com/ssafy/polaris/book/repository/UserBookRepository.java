package com.ssafy.polaris.book.repository;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

	@Query("SELECT NEW com.ssafy.polaris.book.dto.UserBookResponseDto(ub.id, b.cover, b.title, b.author, b.isbn, b.publisher, " +
			"b.pubDate, b.bookDescription, ub.userBookDescription, b.priceStandard, ub.userBookPrice, ub.isOpened, ub.isOwned, " +
			"ub.userBookTradeType, b.seriesId, s.name) " +
			"FROM UserBook ub " +
			"LEFT JOIN Book b ON ub.book.isbn = b.isbn " +
			"LEFT JOIN Series s ON b.seriesId = s.id " +
			"WHERE ub.user.id = :userId")
	List<UserBookResponseDto> findAllByUserId(@Param("userId") Long userId);

	@Query("SELECT NEW com.ssafy.polaris.book.dto.UserBookResponseDto(ub.id, b.cover, b.title, b.author, b.isbn, b.publisher, " +
			"b.pubDate, b.bookDescription, ub.userBookDescription, b.priceStandard, ub.userBookPrice, ub.isOpened, ub.isOwned, " +
			"ub.userBookTradeType, b.seriesId, s.name) " +
			"FROM UserBook ub " +
			"LEFT JOIN Book b ON ub.book.isbn = b.isbn " +
			"LEFT JOIN Series s ON b.seriesId = s.id " +
			"WHERE ub.user.id = :userId " +
			"AND ub.book.isbn = :isbn")
	UserBookResponseDto getUserBook(@Param("userId") Long userId, @Param("isbn") String isbn);

	/**
	 *
	 * @param userId
	 * @param userBookTradeType
	 * @return 거래 가능한 사용자 도서 리스트
	 */
	@Query(value =
			"select new com.ssafy.polaris.trade.dto.TradeBookResponseDto(ub.id, b.isbn, b.title, b.author, ub.isOpened, ub.isOwned, ub.userBookPrice,ub.userBookTradeType,s.id, s.name) from UserBook ub "+
					"left join Book b on ub.book.isbn = b.isbn " +
					"left join Series s on b.seriesId = s.id " +
					"where ub.user.id = :userId and " +
					"ub.isOpened = true and ub.isOwned = true and ub.userBookTradeType = :userBookTradeType")
	List<TradeBookResponseDto> getTradeBookList(@Param("userId") Long userId, @Param("userBookTradeType") UserBookTradeType userBookTradeType);

	@Query("select ub from UserBook ub where ub.book.isbn = :isbn and ub.user.id = :userId")
	UserBook getUserBookByIdAndIsbn(@Param("userId") Long userId, @Param("isbn") String isbn);
}
