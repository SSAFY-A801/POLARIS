package com.ssafy.polaris.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("http://http://i10a801.p.ssafy.io/")
			.allowedMethods("GET", "POST")
			.allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
			.allowCredentials(true);
	}
}
