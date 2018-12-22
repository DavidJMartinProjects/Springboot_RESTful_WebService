package com.project.business.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	public void sendSimpleMessage(String delieveryAddress, String subject, String emailMessage) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(delieveryAddress);
			message.setSubject(subject);
			message.setText(emailMessage);	
			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

}