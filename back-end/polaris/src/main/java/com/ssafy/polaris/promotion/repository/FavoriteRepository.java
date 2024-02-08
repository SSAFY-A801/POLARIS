package com.ssafy.polaris.promotion.repository;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.promotion.domain.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	@Query(value = "select f from Favorite f where f.promotion.id = :promotionId and f.user.id = :userId")
	Optional<Favorite> findFavoriteByPromotionIdAndUserId(@Param("promotionId") Long promotionId, @Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query(value = "insert into favorite (promotion_id, user_id) values (:promotionId, :userId)", nativeQuery = true)
	void saveWithPromotionIdAndUserId(@Param("promotionId") Long promotionId, @Param("userId") Long userId);

	@Query("select count(*) from Favorite f where f.isDeleted = false and f.promotion.id = :promotionId")
	int getFavoriteCount(@Param("promotionId") Long promotionId);

	boolean existsByPromotionIdAndUserId(@Param("promotionId") Long promotinoId, @Param("userId") Long userId);
}
