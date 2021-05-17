package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/{cuenta_id}")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<?> findOne(@PathVariable Long cuenta_id) {
		Cuenta cuenta = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			cuenta = this.cuentaService.findOne(cuenta_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(cuenta == null) {
			response.put("mensaje",	 "La cuenta con id '".concat(cuenta_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Cuenta>(cuenta, HttpStatus.OK);
	}

}
