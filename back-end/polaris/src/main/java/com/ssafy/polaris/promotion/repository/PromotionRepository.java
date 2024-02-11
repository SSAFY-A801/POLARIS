package com.ssafy.polaris.promotion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.connectentity.domain.PromotionUserBook;
import com.ssafy.polaris.promotion.domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

	// 지연로딩 x 한번에 가져와준다.
	@Query(value = "select p from Promotion p "
		+ " join fetch p.promotionUserBooks as pub "
		+ " join fetch pub.userBook as ub "
		+ " join fetch ub.book as b "
		+ "where p.id = :id")
	Optional<Promotion> getJoinedPromotionById(@Param("id") Long id);
}
