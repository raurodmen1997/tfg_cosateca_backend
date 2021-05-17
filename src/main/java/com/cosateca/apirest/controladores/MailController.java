package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.servicios.MailService;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = { "http://localhost:4200"})
public class MailController {

	
	@Autowired
	private MailService mailService;

	@PostMapping("/enviar")
	public ResponseEntity<?> enviarEmail(@RequestParam String to, @RequestParam String subject,
			@RequestParam String body) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			this.mailService.sendMail(to, subject, body);
		} catch (Exception e) {
			response.put("mensaje", "Error al mandar el mail.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Se ha enviado el correo electrónico correctamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	
}
