package com.ssafy.polaris.connectentity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.connectentity.domain.PromotionUserBook;

@Repository
public interface PromotionUserBookRepository extends JpaRepository<PromotionUserBook, Long> {
	@Query(value = "select pub from PromotionUserBook pub "
		+ " join fetch pub.promotion as p "
		+ " join fetch pub.userBook as ub "
		+ "where p.id = :promotionId")
	List<PromotionUserBook> getUserBooksByPromotionId(@Param("promotionId") Long promotionId);
}
