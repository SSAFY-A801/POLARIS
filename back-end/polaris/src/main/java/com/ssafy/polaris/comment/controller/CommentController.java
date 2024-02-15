package com.ssafy.polaris.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.polaris.comment.response.StatusCode;
import com.ssafy.polaris.comment.dto.CommentReponseDto;
import com.ssafy.polaris.comment.dto.CommentRequestDto;
import com.ssafy.polaris.comment.response.DefaultResponse;
import com.ssafy.polaris.comment.service.CommentService;
import com.ssafy.polaris.essay.dto.ScrapDto;
import com.ssafy.polaris.global.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<DefaultResponse<Void>> createComment(@RequestBody CommentRequestDto commentRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {

		commentService.createComment(commentRequestDto, securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.CREATED, StatusCode.SUCCESS_CREATE_COMMENT);
	}

	@GetMapping("/{essayId}")
	public ResponseEntity<DefaultResponse<List<CommentReponseDto>>> getCommentList(@PathVariable("essayId")Long essayId) {
		List<CommentReponseDto> commentList = commentService.getCommentList(essayId);
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_GET_COMMENT_LIST, commentList);
	}

	@PatchMapping
	public ResponseEntity<DefaultResponse<Void>> updateComment(@RequestBody CommentRequestDto commentRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		commentService.updateComment(commentRequestDto, securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_UPDATE_COMMENT);
	}

	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> deleteComment(@RequestBody CommentRequestDto commentRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		commentService.deleteComment(commentRequestDto, securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_DELETE_COMMENT);
	}
}
