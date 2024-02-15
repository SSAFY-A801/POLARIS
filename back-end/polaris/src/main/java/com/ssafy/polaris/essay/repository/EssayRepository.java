package com.ssafy.polaris.essay.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.polaris.essay.dto.EssaySimpleResponseDto;
import com.ssafy.polaris.essay.dto.MostScrappedEssayResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.essay.domain.Essay;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long> {
	Optional<Essay> findById(Long id);

	Optional<Essay> findByUserBookIdAndUserId(Long userBookId, Long userId);

	@Query("select e "
		+ "from Essay e "
		+ "	left join fetch e.user "
		+ "	left join fetch e.userBook "
		+ " left join fetch e.userBook.book "
		+ "where e.deletedAt is null "
		+ "	and :id = e.id")
	Optional<Essay> findJoinedEssayById(@Param("id") Long id);

	@Query("select new com.ssafy.polaris.essay.dto.EssaySimpleResponseDto(" +
			" e.id, e.title, b.title, e.hit, 0, e.createdAt) from Essay e" +
			" left join e.userBook ub" +
			" left join e.userBook.book b " +
			" where e.user.id = :userId")
	List<EssaySimpleResponseDto> getEssayByUserId(@Param("userId") Long userId);

	@Query("select new com.ssafy.polaris.essay.dto.MostScrappedEssayResponseDto(" +
			" b.cover, b.author, b.title, 0, b.bookDescription, " +
			" e.user.id, e.user.nickname, e.id) from Essay e " +
			" right join Scrap s on e.id = s.essay.id " +
			" left join e.userBook.book b" +
			" group by e.id " +
			" order by count(e.id) desc limit 1")
	MostScrappedEssayResponseDto getMostScrappedEssay();

	@Transactional
	@Modifying
	@Query("delete from Essay e where e.userBookId = :userBookId ")
	void deleteEssayByUserBookId(@Param("userBookId") Long userBookId);
}
