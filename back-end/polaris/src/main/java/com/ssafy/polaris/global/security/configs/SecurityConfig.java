package com.ssafy.polaris.global.security.configs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.polaris.global.security.filter.ExceptionHandlerFilter;
import com.ssafy.polaris.global.security.filter.JwtAuthenticationFilter;
import com.ssafy.polaris.global.security.handler.CustomAccessDeniedHandler;
import com.ssafy.polaris.global.security.provider.JwtTokenProvider;
import com.ssafy.polaris.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final StringRedisTemplate redisTemplate;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic(HttpBasicConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.sessionManagement(configurer ->
				configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/**").permitAll()
					.anyRequest().authenticated())
			// .requestMatchers("/user", "/user/login").permitAll()
			// .requestMatchers("/user/email_check/**", "/user/nickname_check/**").permitAll()
			// .requestMatchers("/user/email_cert").permitAll()
			// .requestMatchers("/send-mail/**").permitAll()
			// .anyRequest().authenticated())
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userRepository, redisTemplate),
				UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
			Arrays.asList("*", "http://i10a801.p.ssafy.io", "https://localhost:5173", "https://i10a801.p.ssafy.io"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomAccessDeniedHandler accessDeniedHandler() {
		// AccessDeniedHandler 객체를 생성하고, errorPage를 setter를 통해 설정한 후, 빈으로 등록
		CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
		return accessDeniedHandler;
	}

}
