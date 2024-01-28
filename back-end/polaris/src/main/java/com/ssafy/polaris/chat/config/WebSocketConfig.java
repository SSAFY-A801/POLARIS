package com.ssafy.polaris.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	// 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// queue - 한명이 메세지를 발행했을 때 발행했을 때 발행한 한명에게 다시 정보를 보내는 경우에 사용
		registry.enableSimpleBroker("/queue");
		registry.enableSimpleBroker("/send");
	}

	@Override
	// connection을 맺을때 CORS 허용합니다.
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/")
			// 스프링 5.3, 스프링부트 2.4 버전 부터 allowCredentials이 true인 경우 setAllowedOrigins 메서드에서
			// 와일드 카드 `'*'`을 사용하실 수 없습니다.
			//  	.setAllowedOrigins("*")
			.setAllowedOriginPatterns("*")
			.withSockJS();
	}
}
