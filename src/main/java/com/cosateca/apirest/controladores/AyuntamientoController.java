package com.cosateca.apirest.controladores;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.entidades.Horario;
import com.cosateca.apirest.entidades.InfoAyuntamiento;
import com.cosateca.apirest.servicios.AyuntamientoService;
import com.cosateca.apirest.servicios.CuentaService;
import com.cosateca.apirest.servicios.HorarioService;

@RestController
@RequestMapping("/api/entidades/ayuntamiento")
@CrossOrigin(origins = { "http://localhost:4200" })
public class AyuntamientoController {

	@Autowired
	private AyuntamientoService ayuntamientoService;
	
	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	private CuentaService cuentaService;
	
	@GetMapping("/obtenerInfo")
	public InfoAyuntamiento obtenerInfoAyuntamiento() {
		InfoAyuntamiento info = new InfoAyuntamiento();
		
		Ayuntamiento ayuntamiento = this.ayuntamientoService.obtenerAyuntamiento();
		List<Horario> horarios = this.horarioService.findAll();
		List<String> infoHorarios = new ArrayList<>();
		Map<String, List<String>> mapa = new HashMap<>();
		
		info.setCodigos_postales(ayuntamiento.getCodigos_postales());
		info.setDireccion_correo(ayuntamiento.getDireccion_email());
		info.setMunicipio(ayuntamiento.getMunicipio());
		info.setProvincia(ayuntamiento.getProvincia());
		info.setTelefono(ayuntamiento.getTelefono());
		info.setDireccion(ayuntamiento.getDireccion());
		
		for(Horario hor: horarios) {
			if(mapa.containsKey(hor.getDia())) {
				mapa.get(hor.getDia()).add(hor.getHora_apertura().toString().concat("-").concat(hor.getHora_cierre().toString()));
			}else {
				List<String> valor = new ArrayList<>();
				valor.add(hor.getHora_apertura().toString().concat("-").concat(hor.getHora_cierre().toString()));
				mapa.put(hor.getDia(), valor);
			}
		}
		
		for(String val:mapa.keySet()) {
			String linea = val.concat(" ");
			for(String fecha:mapa.get(val)) {
				linea = linea + fecha + " ";
			}
			infoHorarios.add(linea.trim());
		}
		
		info.setHorarios(infoHorarios);
		return info;
	}
	
	@PutMapping("/{ayuntamiento_id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody Ayuntamiento ayuntamiento, @PathVariable Long ayuntamiento_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Ayuntamiento ayuntamientoActualizado = null;
		Ayuntamiento ayuntamientoRecuperado = null;		
		Cuenta cuentaActualizada = null;
		Cuenta cuentaRecuperada = null;
		
		if(ayuntamiento.getCuenta() != null) {
			try {
				cuentaRecuperada= this.cuentaService.findOne(ayuntamiento.getCuenta().getId());
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos.");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			
			if(cuentaRecuperada == null) {
				response.put("mensaje",	 "La cuenta con id '".concat(ayuntamiento.getCuenta().getId().toString()).concat("' no existe."));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}	
		}
			
		
		try {
			ayuntamientoRecuperado= this.ayuntamientoService.findOne(ayuntamiento_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(ayuntamientoRecuperado == null) {
			response.put("mensaje",	 "El ayuntamiento con id '".concat(ayuntamiento_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}	
		
		if(ayuntamiento.getCuenta() != null) {	
			try {
				cuentaRecuperada.setNombre_perfil(ayuntamiento.getCuenta().getNombre_perfil());
				cuentaActualizada = this.cuentaService.guardarCuenta(cuentaRecuperada);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el update en la base de datos.");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		}
		
		
		try {

			ayuntamientoRecuperado.setDireccion_email(ayuntamiento.getDireccion_email());
			ayuntamientoRecuperado.setNombre(ayuntamiento.getNombre());
			ayuntamientoRecuperado.setTelefono(ayuntamiento.getTelefono());
			ayuntamientoRecuperado.setDireccion(ayuntamiento.getDireccion());
			if(ayuntamiento.getCuenta() != null) {
				ayuntamientoRecuperado.setCuenta(cuentaActualizada);
			}
			ayuntamientoActualizado = this.ayuntamientoService.guardarAyuntameinto(ayuntamientoRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("ayuntamiento", ayuntamientoActualizado);
		response.put("mensaje", "Los datos del ayuntamiento han sido actualizados con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
	}
	
}
