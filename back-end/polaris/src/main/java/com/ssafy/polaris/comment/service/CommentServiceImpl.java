package com.ssafy.polaris.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.comment.domain.Comment;
import com.ssafy.polaris.comment.dto.CommentReponseDto;
import com.ssafy.polaris.comment.dto.CommentRequestDto;
import com.ssafy.polaris.comment.exception.CommentNotFoundException;
import com.ssafy.polaris.comment.repository.CommentRepository;
import com.ssafy.polaris.essay.exception.EssayNotFoundException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.user.exception.UserNotAuthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{
	private final CommentRepository commentRepository;

	@Override
	@Transactional
	public void createComment(CommentRequestDto commentRequestDto, SecurityUser securityUser) {
		try {
			commentRepository.saveWithEssayIdAndUserId(commentRequestDto.getEssayId(), securityUser.getId(),
				commentRequestDto.getContent());
		} catch (Exception e) {
			e.printStackTrace();
			throw new EssayNotFoundException("댓글 등록 중 발생");
		}
	}

	@Override
	public List<CommentReponseDto> getCommentList(Long essayId) {
		return commentRepository.getCommentsByEssayId(essayId);
	}

	@Override
	@Transactional
	public void updateComment(CommentRequestDto commentRequestDto, SecurityUser securityUser) {
		Comment comment = commentRepository.findCommentById(commentRequestDto.getId())
			.orElseThrow(() -> new CommentNotFoundException(""));
		if (!comment.getUser().getId().equals(securityUser.getId())) {
			throw new UserNotAuthorizedException("");
		}
		comment.updateComment(commentRequestDto.getContent());
	}

	@Override
	@Transactional
	public void deleteComment(CommentRequestDto commentRequestDto, SecurityUser securityUser) {
		Comment comment = commentRepository.findCommentById(commentRequestDto.getId())
			.orElseThrow(() -> new CommentNotFoundException(""));
		if (!comment.getUser().getId().equals(securityUser.getId())) {
			throw new UserNotAuthorizedException("");
		}
		comment.deleteComment();
	}
}
