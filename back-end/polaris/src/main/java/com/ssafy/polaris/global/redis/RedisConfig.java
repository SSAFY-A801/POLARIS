package com.ssafy.polaris.global.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String redisHostName;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	@Bean
	public RedisConnectionFactory lettuceConnectionFactory() {
		return new LettuceConnectionFactory(redisHostName, redisPort);
	}

	@Bean
	public RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate = new RedisTemplate<>();
		chatRedisTemplate.setConnectionFactory(connectionFactory);
		chatRedisTemplate.setKeySerializer(new StringRedisSerializer());
		chatRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageSaveDto.class));
		return chatRedisTemplate;
	}
}
