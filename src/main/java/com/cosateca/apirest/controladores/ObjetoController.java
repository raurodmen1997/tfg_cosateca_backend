package com.cosateca.apirest.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.servicios.ObjetoService;

@RestController
@RequestMapping("/api/entidades/objetos")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ObjetoController {
	
	@Autowired
	private ObjetoService objetoService;
	
	
	@GetMapping("")
	public List<Objeto> findAll() {
		return this.objetoService.findAllObjetos();
	}
	
	
	
	

}
