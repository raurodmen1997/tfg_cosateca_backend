package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	
	@GetMapping("/{objeto_id}")
	public ResponseEntity<?> findOne(@PathVariable Long objeto_id) {
		Objeto objeto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			objeto = this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objeto == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Objeto>(objeto, HttpStatus.OK);
	}
	
	
	
	

}
