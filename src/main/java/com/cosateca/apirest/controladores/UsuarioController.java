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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.CarroCompra;
import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.enumerados.TipoIdentificacion;
import com.cosateca.apirest.servicios.CarroCompraService;
import com.cosateca.apirest.servicios.CuentaService;
import com.cosateca.apirest.servicios.UsuarioService;
import com.cosateca.apirest.utilidades.MD5;
import com.cosateca.apirest.utilidades.NIE;
import com.cosateca.apirest.utilidades.NIF;

@RestController
@RequestMapping("/api/entidades/usuarios")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private CarroCompraService carroCompraService;
	
	
	@PostMapping("")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Usuario usuarioNuevo= null;
		Cuenta cuenta = new Cuenta();
		Cuenta cuentaNueva = null;
		CarroCompra carroCompra =  new CarroCompra();
		CarroCompra carroCompraNuevo = null;
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}	
		
		
		if(usuario.getTipo_identificacion().equals(TipoIdentificacion.NIF.name())) {
			if(!NIF.validar(usuario.getCodigo_identificacion())) {
				response.put("Error en el campo 'codigo_identificacion'", "'".concat(usuario.getCodigo_identificacion()).concat("' no es un NIF válido."));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
			}
		}else {
			if(!NIE.calculaNie(usuario.getCodigo_identificacion().substring(0, usuario.getCodigo_identificacion().length()-1)).equals(usuario.getCodigo_identificacion())) {
				response.put("Error en el campo 'codigo_identificacion'", "'".concat(usuario.getCodigo_identificacion()).concat("' no es un NIE válido."));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
			}
		}
		
		
		try {
			cuenta.setNombre_perfil(usuario.getCuenta().getNombre_perfil());	
			cuenta.setPass(MD5.calcularHash(usuario.getCuenta().getPass().getBytes()));
			cuenta.setAutoridad(usuario.getCuenta().getAutoridad());
			cuentaNueva = this.cuentaService.guardarCuenta(cuenta);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la tabla 'cuentas' de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		try {
			carroCompraNuevo = this.carroCompraService.guardarCarroCompra(carroCompra);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la tabla 'carros_compra' de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
		
		try {
			usuario.setCuenta(cuentaNueva);
			usuario.setCarro_compra(carroCompraNuevo);
			usuarioNuevo = this.usuarioService.guardarUsuario(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la tabla 'Usuarios' de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		response.put("usuario", usuarioNuevo);
		response.put("mensaje", "El usuario se ha dado de alta con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
}
