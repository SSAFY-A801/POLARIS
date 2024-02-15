package com.ssafy.polaris.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.comment.domain.Comment;
import com.ssafy.polaris.comment.dto.CommentReponseDto;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Modifying
	@Query(value = "insert into comment(`essay_id`, `user_id`, `content`, `created_at`) values (:essayId, :userId, :content, now())", nativeQuery = true)
	void saveWithEssayIdAndUserId(
		@Param("essayId")Long essayId,
		@Param("userId")Long userId,
		@Param("content")String content);

	@Query(value = "select new com.ssafy.polaris.comment.dto.CommentReponseDto( "
		+ "c.id, "
		+ "new com.ssafy.polaris.user.dto.UserResponseDto(c.user), "
		+ "c.createdAt, "
		+ "c.content) "
		+ "from Comment c where c.essay.id = :essayId and c.deletedAt is null ")
	List<CommentReponseDto> getCommentsByEssayId(@Param("essayId")Long essayId);

	Optional<Comment> findCommentById(Long id);
}
