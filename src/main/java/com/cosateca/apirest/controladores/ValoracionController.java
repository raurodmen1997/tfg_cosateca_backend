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

import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.entidades.Valoracion;
import com.cosateca.apirest.servicios.ObjetoService;
import com.cosateca.apirest.servicios.UsuarioService;
import com.cosateca.apirest.servicios.ValoracionService;

@RestController
@RequestMapping("/api/entidades/valoraciones")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ValoracionController {

	
	@Autowired
	private ValoracionService valoracionService;
	
	@Autowired
	private ObjetoService objetoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping("/{valoracion_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> findOne(@PathVariable Long valoracion_id) {
		Valoracion valoracion= null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			valoracion= this.valoracionService.findOne(valoracion_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(valoracion == null) {
			response.put("mensaje",	 "La valoración con id '".concat(valoracion_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Valoracion>(valoracion, HttpStatus.OK);
	}
	
	
	@PostMapping("")
	@Secured("ROLE_USER")
	public ResponseEntity<?> crearValoracion(@Valid @RequestBody Valoracion valoracion, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Valoracion valoracionNueva= null;
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		try {
			valoracionNueva= this.valoracionService.guardarValoracion(valoracion);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("valoracion", valoracionNueva);
		response.put("mensaje", "La valoración ha sido creada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
	@DeleteMapping("/{valoracion_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> eliminarValoracion(@PathVariable Long valoracion_id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Valoracion valoracion = null;
		
		try {
			valoracion = this.valoracionService.findOne(valoracion_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(valoracion == null) {
			response.put("mensaje",	 "La valoración con id '".concat(valoracion_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			this.valoracionService.eliminarValoracion(valoracion);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "La valoración ha sido borrada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);	
	}
	
	
	@PutMapping("/{valoracion_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> actualizarValoracion(@Valid @RequestBody Valoracion valoracion, @PathVariable Long valoracion_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Valoracion valoracionActualizada = null;
		Valoracion valoracionRecuperada = null;
		
		try {
			valoracionRecuperada= this.valoracionService.findOne(valoracion_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(valoracionRecuperada == null) {
			response.put("mensaje",	 "La valoración con id '".concat(valoracion_id.toString()).concat("' no existe."));
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
			valoracionRecuperada.setComentario(valoracion.getComentario());
			valoracionRecuperada.setPuntuacion(valoracion.getPuntuacion());
			valoracionActualizada = this.valoracionService.guardarValoracion(valoracionRecuperada);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("valoracion", valoracionActualizada);
		response.put("mensaje", "La valoración ha sido actualizada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
	
	@GetMapping("/objeto/{objeto_id}")
	public ResponseEntity<?> valoracionesObjeto(@PathVariable Long objeto_id) {
		List<Valoracion> valoraciones= null;
		Objeto objeto = null;
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			objeto= this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objeto == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			valoraciones= this.valoracionService.valoracionesObjeto(objeto.getId());
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
		
		return new ResponseEntity<List<Valoracion>>(valoraciones, HttpStatus.OK);
	}
	
	
	@GetMapping("/usuario/{usuario_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> valoracionesUsuario(@PathVariable Long usuario_id) {
		List<Valoracion> valoraciones= null;
		Usuario usuario = null;
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			usuario= this.usuarioService.findOne(usuario_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(usuario == null) {
			response.put("mensaje",	 "El usuario con id '".concat(usuario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			valoraciones= this.valoracionService.valoracionesUsuario(usuario_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
		
		return new ResponseEntity<List<Valoracion>>(valoraciones, HttpStatus.OK);
	}
	
	
	@GetMapping("/objeto/media/{objeto_id}")
	public ResponseEntity<?> valoracionMediaObjeto(@PathVariable Long objeto_id) {
		Objeto objeto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			objeto= this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objeto == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		Double r = this.valoracionService.valoracionMediaObjeto(objeto_id);
		return new ResponseEntity<Double>(r, HttpStatus.OK);
	}
	
	
	
	
}
