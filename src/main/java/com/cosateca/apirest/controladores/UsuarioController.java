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

import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.enumerados.TipoIdentificacion;
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
	
	
	@GetMapping("/olvidados")
	public List<Usuario> findAll() {
		return this.usuarioService.usuarioASerOlvidados();
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Usuario usuarioNuevo= null;
		Cuenta cuenta = new Cuenta();
		Cuenta cuentaNueva = null;
		
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
			usuario.setCuenta(cuentaNueva);
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
	
	
	@PutMapping("/{usuario_id}")
	@Secured("ROLE_USER")
	public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody Usuario usuario, @PathVariable Long usuario_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Usuario usuarioActualizado = null;
		Usuario usuarioRecuperado = null;		
		Cuenta cuentaActualizada = null;
		Cuenta cuentaRecuperada = null;
		
		if(usuario.getCuenta() != null) {
			try {
				cuentaRecuperada= this.cuentaService.findOne(usuario.getCuenta().getId());
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos.");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			
			if(cuentaRecuperada == null) {
				response.put("mensaje",	 "La cuenta con id '".concat(usuario.getCuenta().getId().toString()).concat("' no existe."));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}	
		}
			
		
		try {
			usuarioRecuperado= this.usuarioService.findOne(usuario_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(usuarioRecuperado == null) {
			response.put("mensaje",	 "El usuario con id '".concat(usuario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
				.map(err -> "Error en el campo '" + err.getField() + "': " + err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}	
		
		if(usuario.getCuenta() != null) {	
			try {
				cuentaRecuperada.setNombre_perfil(usuario.getCuenta().getNombre_perfil());
				cuentaActualizada = this.cuentaService.guardarCuenta(cuentaRecuperada);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el update en la base de datos.");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		}
		
		
		try {
			usuarioRecuperado.setCodigo_identificacion(usuario.getCodigo_identificacion());
			usuarioRecuperado.setCodigo_postal(usuario.getCodigo_postal());
			usuarioRecuperado.setDireccion_email(usuario.getDireccion_email());
			usuarioRecuperado.setNombre(usuario.getNombre());
			usuarioRecuperado.setPrimer_apellido(usuario.getPrimer_apellido());
			usuarioRecuperado.setSegundo_apellido(usuario.getSegundo_apellido());
			usuarioRecuperado.setTelefono(usuario.getTelefono());
			usuarioRecuperado.setTipo_identificacion(usuario.getTipo_identificacion());
			if(usuario.getCuenta() != null) {
				usuarioRecuperado.setCuenta(cuentaActualizada);
			}
			usuarioActualizado = this.usuarioService.guardarUsuario(usuarioRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("usuario", usuarioActualizado);
		response.put("mensaje", "El usuario ha sido actualizado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
	}
	
	
	
	@DeleteMapping("/{usuario_id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long usuario_id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Usuario usuario = null;
		
		try {
			usuario = this.usuarioService.findOne(usuario_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(usuario == null) {
			response.put("mensaje",	 "El usuario con id '".concat(usuario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(!usuario.getOlvidado()) {
			response.put("mensaje",	 "El usuario no puede ser eliminado porque no ha solicitado el derecho a ser olvidado.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			this.usuarioService.eliminarUsuario(usuario);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "El usuario ha sido borrado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	
	
	
	@PutMapping("/peticionOlvido/{usuario_id}")
	public ResponseEntity<?> realizarPeticionOlvido(@PathVariable Long usuario_id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Usuario usuario_recuperado = null;
		Usuario usuario_actualizado = null;
		
		try {
			usuario_recuperado = this.usuarioService.findOne(usuario_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(usuario_recuperado == null) {
			response.put("mensaje",	 "El usuario con id '".concat(usuario_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(usuario_recuperado.getOlvidado()) {
			response.put("mensaje",	 "El usuario ya ha realizado la petición de olvido.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			usuario_recuperado.setOlvidado(true);
			usuario_actualizado = this.usuarioService.guardarUsuario(usuario_recuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "El usuario ha realizado la petición de olvido correctamente.");
		response.put("texto", "Pronto se procederá a eliminar sus datos del sistema.");
		response.put("usuario", usuario_actualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
		
		
	}
	
	
}
