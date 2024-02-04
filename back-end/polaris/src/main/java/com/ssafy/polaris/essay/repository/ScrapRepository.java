package com.ssafy.polaris.essay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.essay.domain.Scrap;
import com.ssafy.polaris.essay.dto.ScrapDto;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
	@Query(value = "select s.id from Scrap s where essay_id = :essayId and user_id = :userId", nativeQuery = true)
	Optional<Long> findScrapByEssayIdAndUserID(@Param("essayId") Long essayId, @Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query(value = "insert into scrap (essay_id, user_id) values (:essayId, :userId)", nativeQuery = true)
	void saveWithEssayIdAndUserId(@Param("essayId") Long essayId, @Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query(value = "delete from scrap where essay_id = :essayId and user_id = :userId", nativeQuery = true)
	void deleteWithEssayIdAndUserId(@Param("essayId") Long essayId, @Param("userId") Long userId);
}
