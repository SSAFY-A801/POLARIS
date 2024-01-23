package com.ssafy.polaris.domain.comment;

import java.time.LocalDateTime;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.domain.essay.Essay;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String Content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "essay_id")
	private Essay essay;

	public Comment(User user, String content, Essay essay) {
		this.user = user;
		Content = content;
		this.essay = essay;
	}

	public void deleteComment(LocalDateTime now) {
		setDeletedAt(now);
	}
}
