package com.ssafy.polaris.email.service;

import java.util.Random;

// import javax.mail.MessagingException;
// import javax.mail.internet.MimeMessage;

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
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// import server.email.entity.EmailMessage;
// import server.user.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	private final EntityManager em;
	private final UserService userService;

	public String sendMail(EmailMessage emailMessage, String type) {
		String authNum = createCode();

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		// TODO: 비밀번호 재설정을 이메일만으로 하면 안될 것 같음
		// if (type.equals("password")) userService.SetTempPassword(emailMessage.getTo(), authNum);
		User userByEmail = null;
		try {
			userByEmail = userService.getUserByEmail(emailMessage.getTo());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		userService.setPassword(userByEmail.getId(), authNum);

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
			mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
			mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
			javaMailSender.send(mimeMessage);

			log.info("Success");

			return authNum;

		} catch (MessagingException e) {
			log.info("fail");
			throw new RuntimeException(e);
		}
	}

	// 인증번호 및 임시 비밀번호 생성 메서드
	public String createCode() {
		Random random = new Random();
		StringBuffer key = new StringBuffer();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(4);

			switch (index) {
				case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
				case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
				default: key.append(random.nextInt(9));
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
