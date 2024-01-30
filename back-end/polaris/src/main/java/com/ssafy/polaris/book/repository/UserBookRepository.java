package com.ssafy.polaris.book.repository;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.trade.dto.TradeBookResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, String> {

    @Query("select ub.id, b.isbn, b.title, b.cover, b.author, ub.isOpened, ub.isOwned, " +
            "b.series.id, b.series.name from UserBook ub inner join Book b on ub.book = b.isbn " +
            "where ub.user =: userId")
    List<UserBookResponse> findAllByUserId(@Param("userId") Long userId);

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
}

