package com.ssafy.polaris.comment.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.ssafy.polaris.user.dto.UserResponseDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentReponseDto {
	Long id;
	UserResponseDto user;
	LocalDateTime createdAt;
	String comment;
}
