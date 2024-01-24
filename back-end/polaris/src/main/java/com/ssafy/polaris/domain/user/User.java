package com.ssafy.polaris.domain.user;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.domain.book.UserBook;
import com.ssafy.polaris.domain.essay.Essay;
import com.ssafy.polaris.domain.essay.Scrap;
import com.ssafy.polaris.domain.following.Follow;
import com.ssafy.polaris.domain.promotion.Promotion;
import com.ssafy.polaris.domain.regcode.Regcode;
import com.ssafy.polaris.domain.report.Report;
import com.ssafy.polaris.domain.token.Token;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
	// 지역코드
	@OneToOne
	@JoinColumn(name = "regcode_id")
	private Regcode regcode;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Essay> essays;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private Token token;

	@Column(length = 300)
	private String password;

	@Nullable
	private String email;

	private String nickname;

	@Column(length = 3000)
	private String profileUrl;

	@Column(length = 600)
	private String introduction;

	@Nullable
	@Column(length = 10)
	private String oauth;

	@OneToMany(mappedBy = "follower")
	private List<Follow> followers = new ArrayList<>();

	@OneToMany(mappedBy = "following")
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

	public User(Regcode regcode, List<Essay> essays, Token token, String password, @Nullable String email,
		String nickname,
		@Nullable String oauth, List<Follow> followers, List<Follow> followings, List<UserBook> userBooks,
		List<Promotion> promotions, List<Scrap> scraps, List<Report> reporters, List<Report> reportUserList) {
		this.regcode = regcode;
		this.essays = essays;
		this.token = token;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.oauth = oauth;
		this.followers = followers;
		this.followings = followings;
		this.userBooks = userBooks;
		this.promotions = promotions;
		this.scraps = scraps;
		this.reporters = reporters;
		this.reportUserList = reportUserList;
		// TODO: 기본 프로필 url
		this.profileUrl = "basic Profile URL";
		this.introduction = " ";
	}
}
