package com.ssafy.polaris.security.configs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.polaris.security.filter.JwtAuthenticationFilter;
import com.ssafy.polaris.security.provider.JwtTokenProvider;
import com.ssafy.polaris.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic(HttpBasicConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			// .cors(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
			.sessionManagement(configurer ->
				configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize ->
				authorize
					.anyRequest().permitAll())
					// .requestMatchers("/user", "/user/login").permitAll()
					// .requestMatchers("/user/email_check/**", "/user/nickname_check/**").permitAll()
					// .requestMatchers("/user/email_cert").permitAll()
					// .requestMatchers("/send-mail/**").permitAll()
					// .anyRequest().authenticated())
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userRepository),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// @Bean
	// CorsConfigurationSource corsConfigurationSource() {
	// 	CorsConfiguration configuration = new CorsConfiguration();
	// 	configuration.setAllowedOrigins(Arrays.asList("http://i10a801.p.ssafy.io"));
	// 	configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","FETCH","DELETE"));
	// 	// you can configure many allowed CORS headers
	//
	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	source.registerCorsConfiguration("/**", configuration);
	// 	return source;
	// }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://i10a801.p.ssafy.io"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
