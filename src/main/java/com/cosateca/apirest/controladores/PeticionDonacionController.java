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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.entidades.PeticionDonacion;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.enumerados.EstadoPeticionDonacion;
import com.cosateca.apirest.servicios.AyuntamientoService;
import com.cosateca.apirest.servicios.ImagenService;
import com.cosateca.apirest.servicios.ObjetoService;
import com.cosateca.apirest.servicios.PeticionDonacionService;
import com.cosateca.apirest.servicios.UsuarioService;

@RestController
@RequestMapping("/api/entidades/peticionesDonaciones")
@CrossOrigin(origins = { "http://localhost:4200" })
public class PeticionDonacionController {

	@Autowired
	private PeticionDonacionService peticionDonacionService;
	
	@Autowired
	private ObjetoService objetoService;
	
	@Autowired
	private ImagenService imagenService;
	
	@Autowired
	private AyuntamientoService ayuntamientoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("")
	public List<PeticionDonacion> findAll() {
		return this.peticionDonacionService.findAll();
	}
	
	@GetMapping("page/{page}")
	@Secured("ROLE_USER")
	public Page<PeticionDonacion> peticionesDonacionByUser(@PathVariable Integer page, @RequestParam Long usuario_id) {
		Pageable pageable = PageRequest.of(page, 4);
		return this.peticionDonacionService.peticionesByUsuario(usuario_id, pageable);
	}
	
	
	@GetMapping("pendientes/page/{page}")
	@Secured("ROLE_ADMIN")
	public Page<PeticionDonacion> peticionesPendientesDeRevision(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return this.peticionDonacionService.peticionesPendienteDeRevision(pageable);
	}
	
	@PostMapping("")
	@Secured("ROLE_USER")
	public ResponseEntity<?> crearPeticionDonacion(@Valid @RequestBody PeticionDonacion peticionDonacion, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacionNueva = null;
		Ayuntamiento ayuntamiento = null;
		Usuario usuario = null;	
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		try {
			ayuntamiento = this.ayuntamientoService.obtenerAyuntamiento();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			usuario = this.usuarioService.findOne(peticionDonacion.getUsuario().getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		peticionDonacion.setUsuario(usuario);
		peticionDonacion.setAyuntamiento(ayuntamiento);
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
	
	
	@PutMapping("/aceptar/{peticion_donacion_id}")@Secured("ROLE_ADMIN")
	public ResponseEntity<?> aceptarPeticionDonacion(@PathVariable Long peticion_donacion_id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacionActualizado = null;PeticionDonacion peticionDonacionRecuperado = null;Objeto objetoConstruido = null;Objeto objetoNuevo = null;	
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
		if(!peticionDonacionRecuperado.getEstado().equals(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name())) {
			response.put("mensaje",	 "La petición de donación no se encuentra en estado '".concat(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name()).concat("'."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
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
			response.put("mensaje", "Error al realizar el insert en la tabla en la base de datos.");response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
		response.put("peticionDonacion", peticionDonacionActualizado);response.put("objeto", objetoNuevo);response.put("mensaje", "Se ha añadido el objeto a la lista de objetos del sistema.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 		
	}
	
	
	@PutMapping("/rechazar/{peticion_donacion_id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> rechazarPeticionDonacion(@PathVariable Long peticion_donacion_id) throws Exception {
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
		
		if(!peticionDonacionRecuperado.getEstado().equals(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name())) {
			response.put("mensaje",	 "La petición de donación no se encuentra en estado '".concat(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name()).concat("'."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
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
		response.put("mensaje", "Se ha rechazado la petición de donación correctamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
	@DeleteMapping("/{peticion_donacion_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> eliminarPeticionDonacion(@PathVariable Long peticion_donacion_id) {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacion= null;
		
		try {
			peticionDonacion = this.peticionDonacionService.findOne(peticion_donacion_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(peticionDonacion == null) {
			response.put("mensaje",	 "La petición de donación con id '".concat(peticion_donacion_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(!peticionDonacion.getEstado().equals(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name())) {
			response.put("error", "La petición de donación no se puede eliminar porque su estado no es '".concat(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name()).concat("'."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			this.peticionDonacionService.eliminarPeticionDonacion(peticionDonacion);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "La petición de donación ha sido borrada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);	
	}
	
	
	@PutMapping("/{peticion_donacion_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> actualizarPeticionDonacion(@Valid @RequestBody PeticionDonacion peticionDonacion, @PathVariable Long peticion_donacion_id, BindingResult result) throws Exception {
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
		
		if(!peticionDonacionRecuperado.getEstado().equals(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name())) {
			response.put("error", "La petición de donación no se puede actualizar porque su estado no es '".concat(EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name()).concat("'."));
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
			peticionDonacionRecuperado.setCategoria(peticionDonacion.getCategoria());
			peticionDonacionRecuperado.setDescripcion(peticionDonacion.getDescripcion());
			peticionDonacionRecuperado.setNombre(peticionDonacion.getNombre());
			peticionDonacionRecuperado.setImagen(this.imagenService.findImagenById(peticionDonacion.getImagen().getId()));	
			peticionDonacionActualizado = this.peticionDonacionService.guardaPeticionDonacion(peticionDonacionRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("peticionDonacion", peticionDonacionActualizado);
		response.put("mensaje", "Se ha actualizado la petición de donación con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
	}
	
}
