package com.ssafy.polaris.global.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.polaris.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 필터 검증을 마친 유저가 어디서든 전역적으로 정보를 가져올 때 쓴다.
@Getter
@NoArgsConstructor
public class SecurityUser implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private String nickname;

	public SecurityUser(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.nickname = user.getNickname();
	}

	public SecurityUser(Long id, String email, String password, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	public SecurityUser(Authentication authentication) {

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("AUTHORITY"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	// TODO: 정확하게 알아보기. 탈퇴한 회원인지 보는 것일 확률이 높다..
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
