package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.entidades.PeticionDonacion;
import com.cosateca.apirest.servicios.ObjetoService;
import com.cosateca.apirest.servicios.PeticionDonacionService;

@RestController
@RequestMapping("/api/entidades/peticionesDonaciones")
@CrossOrigin(origins = { "http://localhost:4200" })
public class PeticionDonacionController {

	@Autowired
	private PeticionDonacionService peticionDonacionService;
	
	@Autowired
	private ObjetoService objetoService;

	@GetMapping("")
	public List<PeticionDonacion> findAll() {
		return this.peticionDonacionService.findAll();
	}
	
	
	

	@PostMapping("")
	public ResponseEntity<?> crearPeticionDonacion(@Valid @RequestBody PeticionDonacion peticionDonacion, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacionNueva = null;
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		try {
			peticionDonacionNueva = peticionDonacionService.guardaPeticionDonacion(peticionDonacion);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("peticionDonacion", peticionDonacionNueva);
		response.put("mensaje", "Se ha creado la petición de donación con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	@PutMapping("/aceptar/{peticion_donacion_id}")
	public ResponseEntity<?> aceptarPeticionDonacion(@Valid @RequestBody PeticionDonacion peticionDonacion, @PathVariable Long peticion_donacion_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacionActualizado = null;
		PeticionDonacion peticionDonacionRecuperado = null;
		Objeto objetoConstruido = null;
		Objeto objetoNuevo = null;
		
		try {
			peticionDonacionRecuperado = this.peticionDonacionService.findOne(peticion_donacion_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(peticionDonacionRecuperado == null) {
			response.put("mensaje",	 "La petición de donacion con id '".concat(peticion_donacion_id.toString()).concat("' no existe."));
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
			peticionDonacionRecuperado.setEstado("ACEPTADA");
			peticionDonacionActualizado = this.peticionDonacionService.guardaPeticionDonacion(peticionDonacionRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		try {
			objetoConstruido = new Objeto();
			objetoConstruido.setAyuntamiento(peticionDonacionActualizado.getAyuntamiento());
			objetoConstruido.setCategoria(peticionDonacionActualizado.getCategoria());
			objetoConstruido.setDescripcion(peticionDonacionActualizado.getDescripcion());
			objetoConstruido.setNombre(peticionDonacionActualizado.getNombre());
			objetoConstruido.setImagen(peticionDonacionActualizado.getImagen());
			objetoNuevo = this.objetoService.guardarObjeto(objetoConstruido);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la tabla en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("peticionDonacion", peticionDonacionActualizado);
		response.put("objeto", objetoNuevo);
		response.put("mensaje", "Se ha creado el objeto con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
	@PutMapping("/rechazar/{peticion_donacion_id}")
	public ResponseEntity<?> rechazarPeticionDonacion(@Valid @RequestBody PeticionDonacion peticionDonacion, @PathVariable Long peticion_donacion_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacionActualizado = null;
		PeticionDonacion peticionDonacionRecuperado = null;
		
		try {
			peticionDonacionRecuperado = this.peticionDonacionService.findOne(peticion_donacion_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(peticionDonacionRecuperado == null) {
			response.put("mensaje",	 "La petición de donacion con id '".concat(peticion_donacion_id.toString()).concat("' no existe."));
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
			peticionDonacionRecuperado.setEstado("RECHAZADA");
			peticionDonacionActualizado = this.peticionDonacionService.guardaPeticionDonacion(peticionDonacionRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("peticionDonacion", peticionDonacionActualizado);
		response.put("mensaje", "Se ha rechazado la petición de donación con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
}
