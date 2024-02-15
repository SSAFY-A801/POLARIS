package com.ssafy.polaris.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.domain.Scrap;
import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.global.BaseEntity;
import com.ssafy.polaris.regcode.domain.Regcode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@SQLDelete(sql = "update users set deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
public class User extends BaseEntity {
	// 지역코드
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "regcode_id", updatable = false, insertable = false)
	private Regcode regcode;

	@Column(name = "regcode_id")
	private Long regcodeId;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Essay> essays;

	@NotNull
	@Column(length = 300)
	private String password;

	private String email;

	@NotNull
	private String nickname;

	@NotNull
	@Column(length = 10000)
	@ColumnDefault(value = "https://polaris-bucket.s3.amazonaws.com/polaris_logo.png")
	private String profileUrl;

	@NotNull
	@Column(length = 600)
	private String introduction;

	@Column(length = 10)
	private String oauth;

	@OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Follow> followers = new ArrayList<>();

	@OneToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Follow> followings = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserBook> userBooks = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Scrap> scraps = new ArrayList<>();

	public void UpdateProfile(Regcode regcode, String nickname, String introduction, String profileUrl) {
		this.regcode = regcode;
		this.regcodeId = regcode.getId();
		this.nickname = nickname;
		this.introduction = introduction;
		this.profileUrl = profileUrl;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
