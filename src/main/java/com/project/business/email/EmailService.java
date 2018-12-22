package com.project.business.email;

public interface EmailService {
		void sendSimpleMessage(String to, String subject, String text);
}