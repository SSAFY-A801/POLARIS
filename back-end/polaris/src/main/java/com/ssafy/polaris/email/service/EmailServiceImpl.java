package com.ssafy.polaris.email.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.ssafy.polaris.email.domain.EmailMessage;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	private final UserService userService;
	private final StringRedisTemplate redisTemplate;

	public void sendMail(EmailMessage emailMessage, String type) {
		String authNum = createCode();

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		log.info("Email Send to " + emailMessage.getTo());


		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
			mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
			mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
			javaMailSender.send(mimeMessage);

			log.info("Email Send to " + emailMessage.getTo() + "Success");

			if ("password".equals(type)) {
				User userByEmail = userService.getUserByEmail(emailMessage.getTo());
				userService.setPassword(userByEmail.getId(), authNum);
			}
			else {
				// TODO: 검사시간 3분. front에게 알리기!
				redisTemplate.opsForValue().set("emailCode:"+emailMessage.getTo(), authNum, 3 * 60 * 1000L, TimeUnit.MILLISECONDS);
			}

		} catch (MessagingException e) {
			log.info("fail");
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw e;
		}
	}

	// 인증번호 및 임시 비밀번호 생성 메서드
	public String createCode() {
		Random random = new Random();
		StringBuffer key = new StringBuffer();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(4);

			switch (index) {
				case 0:
					key.append((char)((int)random.nextInt(26) + 97));
					break;
				case 1:
					key.append((char)((int)random.nextInt(26) + 65));
					break;
				default:
					key.append(random.nextInt(9));
			}
		}
		return key.toString();
	}

	// thymeleaf를 통한 html 적용
	public String setContext(String code, String type) {
		Context context = new Context();
		context.setVariable("code", code);
		return templateEngine.process(type, context);
	}
}
