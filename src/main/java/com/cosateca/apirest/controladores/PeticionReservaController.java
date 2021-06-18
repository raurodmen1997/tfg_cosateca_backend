package com.cosateca.apirest.controladores;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.entidades.PeticionReserva;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.enumerados.EstadoPeticionReserva;
import com.cosateca.apirest.servicios.AyuntamientoService;
import com.cosateca.apirest.servicios.ObjetoService;
import com.cosateca.apirest.servicios.PeticionReservaService;
import com.cosateca.apirest.servicios.UsuarioService;

@RestController
@RequestMapping("/api/entidades/peticionesReserva")
@CrossOrigin(origins = { "http://localhost:4200" })
public class PeticionReservaController {

	@Autowired
	private AyuntamientoService ayuntamientoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PeticionReservaService peticionReservaService;
	
	@Autowired
	private ObjetoService objetoService;
	
	
	
	
	@GetMapping("page/{page}")
	@Secured("ROLE_ADMIN")
	public Page<PeticionReserva> findAll(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return this.peticionReservaService.findAllByPage(pageable);
	}
	
	
	@PostMapping("")
	@Secured("ROLE_USER")
	public ResponseEntity<?> crearPeticionReserva(@Valid @RequestBody PeticionReserva peticionReserva, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionReserva peticionReservaNueva = null;
		Ayuntamiento ayuntamiento = null;
		Usuario usuario = null;

		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		System.out.println(peticionReserva.getFecha_fin_reserva().equals(peticionReserva.getFecha_inicio_reserva()));
		if(peticionReserva.getFecha_fin_reserva().before(peticionReserva.getFecha_inicio_reserva()) && !peticionReserva.getFecha_fin_reserva().equals(peticionReserva.getFecha_inicio_reserva())) {
			response.put("error", "La fecha de finalización no puede ser anterior a la fecha de inicio de la reserva.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}	
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		
		peticionReserva.getFecha_inicio_reserva().setSeconds(1);
		peticionReserva.getFecha_fin_reserva().setSeconds(1);
		
		
		if(peticionReserva.getFecha_inicio_reserva().compareTo(cal.getTime()) < 0 || peticionReserva.getFecha_fin_reserva().compareTo(cal.getTime()) < 0) {
			response.put("error", "La fecha de inicio y finalización de una reserva no puede ser anterior a la fecha actual.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		for(PeticionReserva peticion: this.peticionReservaService.peticionesObjeto(peticionReserva.getObjeto().getId())) {
			
			if(
					((peticionReserva.getFecha_inicio_reserva().after(peticion.getFecha_inicio_reserva()) && 
					peticionReserva.getFecha_inicio_reserva().before(peticion.getFecha_fin_reserva())) || 
					(peticionReserva.getFecha_fin_reserva().after(peticion.getFecha_inicio_reserva()) && 
							peticionReserva.getFecha_fin_reserva().before(peticion.getFecha_fin_reserva()))) 
					
					|| 
					
					((peticion.getFecha_inicio_reserva().after(peticionReserva.getFecha_inicio_reserva()) && 
					peticion.getFecha_inicio_reserva().before(peticionReserva.getFecha_fin_reserva())) || 
					(peticion.getFecha_fin_reserva().after(peticionReserva.getFecha_inicio_reserva()) && 
							peticion.getFecha_fin_reserva().before(peticionReserva.getFecha_fin_reserva()))) 
			) {
				
				response.put("error", "El rango de fechas introducido no está disponible.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				
			}
		}
		
		
		try {
			ayuntamiento = this.ayuntamientoService.obtenerAyuntamiento();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			usuario = this.usuarioService.findOne(peticionReserva.getUsuario().getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		peticionReserva.setUsuario(usuario);
		peticionReserva.setAyuntamiento(ayuntamiento);
		try {
			peticionReservaNueva = this.peticionReservaService.guardar(peticionReserva);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("peticionReserva", peticionReservaNueva);
		response.put("mensaje", "Se ha creado la petición de reserva con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	/*
	@PutMapping("realizarPeticion")
	@Secured("ROLE_USER")
	public ResponseEntity<?> realizarPeticionReserva(@Valid @RequestBody List<PeticionReserva> peticionesReserva, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		List<PeticionReserva> peticiones = new ArrayList<>();
		
		
		for(PeticionReserva p: peticionesReserva) {
			PeticionReserva peticionReservaNueva = null;
			//p.setRealizada(true);
			//p.setFecha_realizada(cal.getTime());
			try {
				peticionReservaNueva = this.peticionReservaService.guardar(p);
				peticiones.add(peticionReservaNueva);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		response.put("mensaje", "Se ha realizado la reserva con éxito.");
		response.put("peticiones", peticiones);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	*/
	
	
	@GetMapping("/usuario/{usuario_id}/page/{page}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> peticionesReservaUsuario(@PathVariable Long usuario_id, @PathVariable Integer page) {
		Page<PeticionReserva> peticionesReserva= null;
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<String, Object>();
		Pageable pageable = PageRequest.of(page, 4);
		
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
			peticionesReserva= this.peticionReservaService.peticionesUsuario(usuario_id, pageable);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
		
		return new ResponseEntity<Page<PeticionReserva>>(peticionesReserva, HttpStatus.OK);
	}
	
	
	@PutMapping("/entregarPeticion/{peticion_id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> entregarPeticionReserva(@PathVariable Long peticion_id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionReserva peticionRecuperada = null;
		PeticionReserva peticionActualizada = null;
		
		try {
			peticionRecuperada= this.peticionReservaService.findOne(peticion_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(peticionRecuperada == null) {
			response.put("mensaje",	 "La petición de reserva con id '".concat(peticion_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			peticionRecuperada.setEstado(EstadoPeticionReserva.EN_PROPIEDAD.name());
			peticionActualizada = this.peticionReservaService.guardar(peticionRecuperada);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje", "Se ha entregado la herramienta correctamente.");
		response.put("peticionReserva", peticionActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	@PutMapping("/finalizarPeticion/{peticion_id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> finalizarPeticion(@PathVariable Long peticion_id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionReserva peticionRecuperada = null;
		PeticionReserva peticionActualizada = null;
		
		try {
			peticionRecuperada= this.peticionReservaService.findOne(peticion_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(peticionRecuperada == null) {
			response.put("mensaje",	 "La petición de reserva con id '".concat(peticion_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			peticionRecuperada.setEstado(EstadoPeticionReserva.FINALIZADA.name());
			peticionActualizada = this.peticionReservaService.guardar(peticionRecuperada);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje", "Se ha finalizado la reserva correctamente.");
		response.put("peticionReserva", peticionActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	@GetMapping("/objeto/{objeto_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> peticionesObjeto(@PathVariable Long objeto_id) {
		List<PeticionReserva> peticionesReservaPorObjeto= null;
		Map<String, Object> response = new HashMap<String, Object>();
		Objeto objeto = null;
		
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
			peticionesReservaPorObjeto= this.peticionReservaService.peticionesObjeto(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
		
		response.put("peticionesObjeto", peticionesReservaPorObjeto);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/usuarioCodigo/{codigo_identificacion}/page/{page}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> peticionesReservaUsuario(@PathVariable String codigo_identificacion, @PathVariable Integer page) {
		Page<PeticionReserva> peticionesReserva= null;
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<String, Object>();
		Pageable pageable = PageRequest.of(page, 4);
		
		try {
			usuario= this.usuarioService.usuarioPorCodigoIdentificacion(codigo_identificacion);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(usuario == null) {
			response.put("mensaje",	 "El usuario con codigo de identificación '".concat(codigo_identificacion).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {
			peticionesReserva= this.peticionReservaService.peticionesUsuarioCodigoIdentificacion(codigo_identificacion, pageable);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
		
		return new ResponseEntity<Page<PeticionReserva>>(peticionesReserva, HttpStatus.OK);
	}
	
	
	
}
