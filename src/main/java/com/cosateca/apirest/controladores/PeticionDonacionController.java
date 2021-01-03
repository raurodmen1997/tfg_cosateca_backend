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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.PeticionDonacion;
import com.cosateca.apirest.servicios.PeticionDonacionService;

@RestController
@RequestMapping("/api/entidades/peticionesDonaciones")
@CrossOrigin(origins = { "http://localhost:4200" })
public class PeticionDonacionController {

	@Autowired
	private PeticionDonacionService peticionDonacionService;

	@GetMapping("")
	public List<PeticionDonacion> findAll() {
		return this.peticionDonacionService.findAll();
	}
	
	
	

	@PostMapping("")
	public ResponseEntity<?> crearPeticionDonacion(@RequestBody PeticionDonacion peticionDonacion) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		PeticionDonacion peticionDonacionNueva = null;
		try {
			peticionDonacionNueva = peticionDonacionService.guardaPeticionDonacion(peticionDonacion);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<PeticionDonacion>(peticionDonacionNueva, HttpStatus.CREATED);

	}

}
