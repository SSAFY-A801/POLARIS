package com.ssafy.polaris.email.service;

import com.ssafy.polaris.email.domain.EmailMessage;

public interface EmailService {
	String sendMail(EmailMessage emailMessage, String type);
}
