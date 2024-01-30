package com.ssafy.polaris.user.domain;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.promotion.domain.Promotion;
import com.ssafy.polaris.regcode.domain.Regcode;
import com.ssafy.polaris.report.domain.Report;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.essay.Scrap;
import com.ssafy.polaris.following.Follow;
import com.ssafy.polaris.token.Token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
	// 지역코드
	@NotNull
	@OneToOne
	@JoinColumn(name = "regcode_id")
	private Regcode regcode;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Essay> essays;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private Token token;

	@NotNull
	@Column(length = 300)
	private String password;

	private String email;

	@NotNull
	private String nickname;

	@NotNull
	@Column(length = 3000)
	// @ColumnDefault(value = "")
	private String profileUrl;

	@NotNull
	@Column(length = 600)
	private String introduction;

	@Column(length = 10)
	private String oauth;

	@OneToMany(mappedBy = "follower", fetch = FetchType.EAGER)
	private List<Follow> followers = new ArrayList<>();

	@OneToMany(mappedBy = "following", fetch = FetchType.EAGER)
	private List<Follow> followings = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<UserBook> userBooks = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Promotion> promotions = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Scrap> scraps = new ArrayList<>();

	@OneToMany(mappedBy = "reporter")
	private List<Report> reporters = new ArrayList<>();

	// TODO: 나는 다른 유저 여러 명을 신고할 수 있다. 재귀적인 관계를 어떻게 풀 것인가?
	@OneToMany(mappedBy = "reportedUser")
	List<Report> reportUserList = new ArrayList<>();

	public void UpdateProfile(Regcode regcode, String nickname, String introduction){
		this.regcode = regcode;
		this.nickname = nickname;
		this.introduction = introduction;
	}
}
