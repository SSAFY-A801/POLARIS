package com.ssafy.polaris.domain.comment;

import java.time.LocalDateTime;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.domain.essay.Essay;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(length = 600)
	private String Content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "essay_id")
	private Essay essay;

	public void deleteComment(LocalDateTime now) {
		setDeletedAt(now);
	}
}
