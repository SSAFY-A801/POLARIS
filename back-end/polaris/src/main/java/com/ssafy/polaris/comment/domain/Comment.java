package com.ssafy.polaris.comment.domain;

import java.time.LocalDateTime;

import com.ssafy.polaris.common.domain.BaseEntity;
import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@Column(length = 600)
	private String content;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "essay_id")
	private Essay essay;

	public void deleteComment(LocalDateTime now) {
		setDeletedAt(now);
	}
}
