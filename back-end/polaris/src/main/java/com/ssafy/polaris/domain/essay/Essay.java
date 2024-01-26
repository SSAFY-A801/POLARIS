package com.ssafy.polaris.domain.essay;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.domain.book.UserBook;
import com.ssafy.polaris.domain.comment.Comment;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Essay extends BaseEntity {
	// fetch = FetchType.EAGER (default)
	// EAGER를 적용한 이유는 독후감을 가져올 때 무조건 User를 가져와야 하기 때문에 한 쿼리에서 가져오기 위해서였다.
	// TODO: EAGER와 LAZY를 적용하는 때의 차이는 무엇인가?
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "user_book_id")
	private UserBook userBook;

	private String title;

	@Column(length = 50000)
	private String content;

	private int hit;

	// private int scrapsAmount;
	// private int repliesAmount;
	@Column(columnDefinition="CHAR(6)")
	private String isOpened;

	@OneToMany(mappedBy = "essay")
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "essay")
	private List<Scrap> scraps = new ArrayList<>();

	/*
	* hit은 생성해주지 않고 기본 값을 0으로 생성한다.
	 */

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

	// public void increaseScrap(){
	// 	scrapsAmount += 1;
	// }
	// public void decreaseScrap(){
	// 	if(scrapsAmount > 0)
	// 		scrapsAmount -= 1;
	// }
	//
	// public void increaseReply() {
	// 	repliesAmount += 1;
	// }
	//
	// public void decreaseReply() {
	// 	if (repliesAmount > 0)
	// 		repliesAmount -= 1;
	// }
}