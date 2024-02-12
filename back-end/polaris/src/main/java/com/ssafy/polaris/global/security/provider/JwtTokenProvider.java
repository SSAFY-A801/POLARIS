package com.ssafy.polaris.global.security.provider;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.global.security.service.CustomUserDetailsService;
import com.ssafy.polaris.global.security.util.SecurityUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	private final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; // 30분
	private final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일

	private Key key;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	// value 방식으로
	// @ConfigurationProperties(prefix = "cloud.aws.s3") yml 파일에 해당 프리픽스로 적힌 값을 가져올 수 있다.
	// @ConstructorBinding
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// 생성
	// AccessToken, RefreshToken 생성
	public Map<String, String> generateToken(long id, String nickName, Authentication authentication) {
		// 로그인 정보 가져오기
		// SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
		SecurityUser securityUser = new SecurityUser(
			id,
			(String)authentication.getPrincipal(),
			(String)authentication.getCredentials(),
			nickName
		);
		String authorities = securityUser.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
		// Long id = securityUser.getId();

		long now = (new Date()).getTime();
		Date accessTokenExpireIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim("auth", authorities) // 넣고싶은 값 넣기
			.claim("id", id)
			.claim("email", securityUser.getEmail())
			.claim("nickname", nickName)
			.setExpiration(accessTokenExpireIn)
			.signWith(key, SignatureAlgorithm.HS256) // 원하는 방식
			.compact();

		log.info("access token : " + accessToken);

		// 리프레쉬 토큰
		Date refreshTokenExpireIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
		String refreshToken = Jwts.builder()
			.setExpiration(refreshTokenExpireIn)
			.signWith(key, SignatureAlgorithm.HS256) // 원하는 방식
			.compact();

		HashMap<String, String> map = new HashMap<>();
		map.put("access", SecurityUtil.getTokenPrefix() + " " + accessToken);
		map.put("refresh", SecurityUtil.getTokenPrefix() + " " + refreshToken);
		return map;
	}

	// 복호화
	public Authentication getAuthentication(String token) {
		// 토큰 복호화
		Claims claims = parseClaims(token);

		// claims.get() 키 값에 맞는 value가 나온다.

		String userName = claims.getSubject();

		// 사용자 이름으로 로그인한 유저 정보 가져오기
		SecurityUser securityUser = (SecurityUser)userDetailsService.loadUserByUsername(userName);

		List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		// TODO: Authentication 타입 찾아서 맞는 것 쓰기
		return new UsernamePasswordAuthenticationToken(securityUser, "", authorities);
	}

	// 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
			throw e;
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
			throw e;
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
			throw e;
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	// parseClaim
	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public long getACCESS_TOKEN_EXPIRE_TIME() {
		return ACCESS_TOKEN_EXPIRE_TIME;
	}

	public long getREFRESH_TOKEN_EXPIRE_TIME() {
		return REFRESH_TOKEN_EXPIRE_TIME;
	}
}
