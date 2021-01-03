package com.cosateca.apirest.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.servicios.CuentaService;

@RestController
@RequestMapping("/api/entidades/cuentas")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CuentaController {
	
	@Autowired
	private CuentaService cuentaService;
	
	
	@GetMapping("")
	public List<Cuenta> findAll() {
		return this.cuentaService.findAll();
	}

}
