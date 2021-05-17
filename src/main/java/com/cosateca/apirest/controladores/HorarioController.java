package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Horario;
import com.cosateca.apirest.servicios.HorarioService;

@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/api/entidades/horarios")
@CrossOrigin(origins = { "http://localhost:4200" })
public class HorarioController {
	
	@Autowired
	private HorarioService horarioService;
	
	
	
	@GetMapping("")
	public List<Horario> findAll() {
		return this.horarioService.findAll();
	}
	
	
	@GetMapping("page/{page}")
	public Page<Horario> findAll(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return this.horarioService.findAllByPage(pageable);
	}
	
	
	@GetMapping("/{horario_id}")
	public ResponseEntity<?> findOne(@PathVariable Long horario_id) {
		Horario horario = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			horario = this.horarioService.findOne(horario_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(horario == null) {
			response.put("mensaje",	 "El horario con id '".concat(horario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Horario>(horario, HttpStatus.OK);
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> crearHorario(@Valid @RequestBody Horario horario, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Horario horarioNuevo = null;
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		try {
			horarioNuevo = horarioService.guardarHorario(horario);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("horario", horarioNuevo);
		response.put("mensaje", "El horario ha sido creado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
	@PutMapping("/{horario_id}")
	public ResponseEntity<?> actualizarHorario(@Valid @RequestBody Horario horario, @PathVariable Long horario_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Horario horarioActualizado = null;
		Horario horarioRecuperado = null;
		
		try {
			horarioRecuperado = this.horarioService.findOne(horario_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(horarioRecuperado == null) {
			response.put("mensaje",	 "El horario con id '".concat(horario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}		
		
		try {
			horarioRecuperado.setDia(horario.getDia());
			horarioRecuperado.setHora_apertura(horario.getHora_apertura());
			horarioRecuperado.setHora_cierre(horario.getHora_cierre());
			horarioRecuperado.setAyuntamiento(horario.getAyuntamiento());
			horarioActualizado = horarioService.guardarHorario(horarioRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("horario", horarioActualizado);
		response.put("mensaje", "El horario ha sido actualizado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
		
	
	@DeleteMapping("/{horario_id}")
	public ResponseEntity<?> eliminarHorario(@PathVariable Long horario_id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Horario horario = null;
		
		try {
			horario = this.horarioService.findOne(horario_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(horario == null) {
			response.put("mensaje",	 "El horario con id '".concat(horario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			this.horarioService.delete(horario);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "El horario ha sido borrado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
		
		
	}
	
	
	
	

}
