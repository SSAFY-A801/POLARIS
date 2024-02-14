package com.ssafy.polaris.essay.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.comment.domain.Comment;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.global.BaseEntity;
import com.ssafy.polaris.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
// @SQLDelete(sql = "update essay set deleted_at = CURRENT_TIMESTAMP where id = ?")
// @SQLRestriction("deleted_at is NULL")
public class Essay extends BaseEntity {
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", updatable = false, insertable = false)
	private User user;

	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@OneToOne
	@JoinColumn(name = "user_book_id", updatable = false, insertable = false)
	private UserBook userBook;

	@Column(name = "user_book_id")
	private Long userBookId;

	@NotNull
	private String title;

	@Column(length = 50000)
	@NotNull
	private String content;

	/*
	 * hit은 생성해주지 않고 기본 값을 0으로 생성한다.
	 */
	@NotNull
	@ColumnDefault(value = "0")
	private int hit;

	@NotNull
	private Boolean isOpened;

	@OneToMany(mappedBy = "essay", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "essay", cascade = CascadeType.ALL)
	private List<Scrap> scraps = new ArrayList<>();

	public void updateHit() {
		hit += 1;
	}

	public void updateEssay(EssayRequestDto essayRequestDto) {
		this.title = essayRequestDto.getTitle();
		this.content = essayRequestDto.getContent();
		this.isOpened = essayRequestDto.getIsOpened();
	}

	public void openEssay() {
		this.setDeletedAt(null);
	}

	public void delete() {
		this.setDeletedAt(LocalDateTime.now());
	}
}
