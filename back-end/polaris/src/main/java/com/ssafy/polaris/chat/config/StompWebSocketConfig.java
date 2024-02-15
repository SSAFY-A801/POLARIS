package com.ssafy.polaris.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	// connection 맺을때 CORS 허용합니다.
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("/stomp/chat") // websocket 핸드쉐이크 커넥션을 생성할 경로
			.setAllowedOriginPatterns("*")
			.withSockJS();
	}

	@Override
	// 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry
			.setApplicationDestinationPrefixes("/pub")
			.enableSimpleBroker("/sub");
	}
}
