package com.ssafy.polaris.essay.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.comment.domain.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class Essay extends BaseEntity {
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@OneToOne
	@JoinColumn(name = "user_book_id")
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
	@Column(columnDefinition="CHAR(6)")
	private String isOpened;

	@OneToMany(mappedBy = "essay")
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "essay")
	private List<Scrap> scraps = new ArrayList<>();


	public void updateHit(){
		hit += 1;
	}

	public void setOpened(){
		isOpened = "공개";
	}
	public void setClosed(){
		isOpened = "비공개";
	}

	public void deleteEssay(LocalDateTime now) {
		setDeletedAt(now);
	}
}
