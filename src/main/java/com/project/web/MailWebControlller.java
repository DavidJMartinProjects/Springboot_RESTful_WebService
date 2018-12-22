package com.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.business.email.EmailServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/mail")
public class MailWebControlller {

	@Autowired
	public EmailServiceImpl emailService;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public String welcome() {
		return "welcome to mail service.";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String createMail(@RequestParam(value = "userName") String userName, @RequestParam(value = "message") String msg) {
		emailService.sendSimpleMessage("davidjmartin@hotmail.com", "poe.ladder feedback delivery confirmation", formatEmailMessage(userName, msg));
		return "mail successfully sent.";
	}

	private String formatEmailMessage(String userName, String msg) {
		return userName + msg;
	}

}	