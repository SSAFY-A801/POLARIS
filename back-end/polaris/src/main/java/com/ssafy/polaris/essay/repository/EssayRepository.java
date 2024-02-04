package com.ssafy.polaris.essay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long> {
	Optional<Essay> findById(Long id);

	@Query("select e "
		+ "from Essay e "
		+ "	left join fetch e.user "
		+ "	left join fetch e.userBook "
		+ " left join fetch e.userBook.book "
		+ "where e.deletedAt is null "
		+ "	and :id = e.id")
	// @Query("select e "
	// 	+ "from Essay e "
	// 	+ "where e.deletedAt is null "
	// 	+ "and :id = e.id")
	Optional<Essay> findJoinedEssayById(@Param("id") Long id);
}
