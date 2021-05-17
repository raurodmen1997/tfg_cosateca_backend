package com.cosateca.apirest.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	

	@Override
	public void sendMail(String to, String subject, String body) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom("raulrodriguezmendez97@gmail.com");
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);

		this.javaMailSender.send(mailMessage);
	}

}
