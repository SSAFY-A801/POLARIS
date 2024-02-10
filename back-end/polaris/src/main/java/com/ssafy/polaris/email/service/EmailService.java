package com.ssafy.polaris.email.service;

import com.ssafy.polaris.email.domain.EmailMessage;

public interface EmailService {
	void sendMail(EmailMessage emailMessage, String type);
}
