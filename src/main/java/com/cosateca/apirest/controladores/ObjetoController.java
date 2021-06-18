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
import org.springframework.web.bind.annotation.PutMapping;
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
	@Secured("ROLE_ADMIN")
	public List<Objeto> findAll() {
		return this.objetoService.findAllObjetos();
	}
	
	@GetMapping("/accesibles")	
	public List<Objeto> objetosAccesibles() {
		return this.objetoService.objetosAccesibles();
	}
	
	@GetMapping("/inaccesibles")
	@Secured("ROLE_ADMIN")
	public List<Objeto> objetosInaccesibles() {
		return this.objetoService.objetosInaccesibles();
	}
	
	@GetMapping("/categoria/{categoria}")	
	public List<Objeto> objetosPorCategoriaAccesible(@PathVariable String categoria) {
		return this.objetoService.objetosPorCategoriaAccesible(categoria);
	}
	
	@GetMapping("/admin/categoria/{categoria}")	
	@Secured("ROLE_ADMIN")
	public List<Objeto> objetosPorCategoriaAdmin(@PathVariable String categoria) {
		return this.objetoService.objetosPorCategoriaAdmin(categoria);
	}
	
	@GetMapping("/admin/categoria/{categoria}/accesible/{accesible}")	
	@Secured("ROLE_ADMIN")
	public List<Objeto> objetosPorCategoriaYAccesibilidad(@PathVariable String categoria, @PathVariable Boolean accesible) {
		return this.objetoService.objetosPorCategoriaAccesibleyAccesibilidad(categoria, accesible);
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
	
	
	@PutMapping("/inhabilitar/{objeto_id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> inhabilitarObjeto(@PathVariable Long objeto_id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Objeto objetoRecuperado = null;
		Objeto objetoActualizado= null;
		
		try {
			objetoRecuperado= this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objetoRecuperado == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			objetoRecuperado.setAccesible(false);
			objetoActualizado = this.objetoService.guardarObjeto(objetoRecuperado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje", "Se ha inhabilitado el objeto correctamente.");
		response.put("objeto", objetoActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	@PutMapping("/habilitar/{objeto_id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> habilitarObjeto(@PathVariable Long objeto_id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Objeto objetoRecuperado = null;
		Objeto objetoActualizado= null;
		
		try {
			objetoRecuperado= this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objetoRecuperado == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			objetoRecuperado.setAccesible(true);
			objetoActualizado = this.objetoService.guardarObjeto(objetoRecuperado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje", "Se ha habilitado el objeto correctamente.");
		response.put("objeto", objetoActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	
	

}
