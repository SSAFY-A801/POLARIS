package com.ssafy.polaris.domain.token;

import com.ssafy.polaris.domain.user.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Token {
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@Nullable
	private String refresh_token;

	public Token(User user, @Nullable String refresh_token) {
		this.user = user;
		this.refresh_token = refresh_token;
	}
}
