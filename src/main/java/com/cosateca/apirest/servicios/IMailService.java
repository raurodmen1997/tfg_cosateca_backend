package com.cosateca.apirest.servicios;

public interface IMailService {
	
	void sendMail(String from, String subject, String body);

}
