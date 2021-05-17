package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.enumerados.Autoridad;
import com.cosateca.apirest.servicios.AyuntamientoService;
import com.cosateca.apirest.servicios.CuentaService;
import com.cosateca.apirest.servicios.UsuarioService;
import com.cosateca.apirest.utilidades.MD5;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = { "http://localhost:4200" })
public class LoginController {
	
	@Autowired
	private CuentaService cuentaSevice;
	
	@Autowired
	private AyuntamientoService ayuntamientoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	
	@GetMapping("")
	public ResponseEntity<?> login(@RequestParam String nombre_perfil, @RequestParam String pass) {
		
		Cuenta cuenta = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		String passMD5 = MD5.calcularHash(pass.getBytes());
		
		try {
			cuenta = this.cuentaSevice.findCuentaByUserAndPass(nombre_perfil, passMD5);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(cuenta == null) {
			response.put("mensaje",	 "El nombre de usuario o contraseña con incorrectos.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(cuenta.getAutoridad().equals(Autoridad.ROLE_ADMIN)) {
			return new ResponseEntity<Ayuntamiento>(this.ayuntamientoService.findAyuntamientoByCuenta(cuenta.getId()), HttpStatus.OK);
		}
		
		return new ResponseEntity<Usuario>(this.usuarioService.findUsuarioByCuenta(cuenta.getId()), HttpStatus.OK);
	}

}
