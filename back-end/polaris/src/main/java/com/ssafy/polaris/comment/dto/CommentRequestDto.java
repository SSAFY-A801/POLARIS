package com.ssafy.polaris.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentRequestDto {
	private Long id;
	private Long userId;
	private Long essayId;
	private String content;
}
