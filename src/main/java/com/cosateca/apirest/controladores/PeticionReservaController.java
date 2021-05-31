package com.cosateca.apirest.controladores;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.PeticionReserva;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.servicios.AyuntamientoService;
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
		
		if(peticionReserva.getFecha_fin_reserva().before(peticionReserva.getFecha_inicio_reserva()) || peticionReserva.getFecha_fin_reserva().equals(peticionReserva.getFecha_inicio_reserva())) {
			response.put("error", "La fecha de finalización no puede ser anterior o igual a la fecha de inicio de la reserva.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}	
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		if(peticionReserva.getFecha_fin_reserva().before(cal.getTime()) || peticionReserva.getFecha_inicio_reserva().before(cal.getTime())) {
			response.put("error", "La fecha de inicio y finalización de una reserva no puede ser anterior a la fecha actual.");
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
}
