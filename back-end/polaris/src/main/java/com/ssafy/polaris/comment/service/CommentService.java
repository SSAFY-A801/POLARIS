package com.ssafy.polaris.comment.service;

import java.util.List;

import com.ssafy.polaris.comment.dto.CommentReponseDto;
import com.ssafy.polaris.comment.dto.CommentRequestDto;
import com.ssafy.polaris.global.security.SecurityUser;

public interface CommentService {
	void createComment(CommentRequestDto commentRequestDto, SecurityUser securityUser);

	List<CommentReponseDto> getCommentList(Long essayId);

	void updateComment(CommentRequestDto commentRequestDto, SecurityUser securityUser);

	void deleteComment(CommentRequestDto commentRequestDto, SecurityUser securityUser);
}
