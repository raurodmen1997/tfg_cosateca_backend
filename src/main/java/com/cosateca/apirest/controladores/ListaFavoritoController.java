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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.ListaFavorito;
import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.servicios.ListaFavoritoService;
import com.cosateca.apirest.servicios.ObjetoService;
import com.cosateca.apirest.servicios.UsuarioService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/entidades/listasFavorito")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ListaFavoritoController {
	
	@Autowired
	private ListaFavoritoService listaFavoritoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ObjetoService objetoService;
	
	
	@GetMapping("/{listaFavorito_id}")
	public ResponseEntity<?> findOne(@PathVariable Long listaFavorito_id) {
		ListaFavorito listaFavorito = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			listaFavorito = this.listaFavoritoService.findOne(listaFavorito_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(listaFavorito == null) {
			response.put("mensaje",	 "La lista de favorito con id '".concat(listaFavorito_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<ListaFavorito>(listaFavorito, HttpStatus.OK);
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> crearListaFavorito(@Valid @RequestBody ListaFavorito listaFavorito, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		ListaFavorito listaFavoritoNuevo = null;
		Usuario usuario = null;
		
		try {
			usuario = this.usuarioService.findOne(listaFavorito.getUsuario().getId());
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(usuario == null) {
			response.put("mensaje",	 "El usuario con id '".concat(listaFavorito.getUsuario().getId().toString()).concat("' no existe."));
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
			listaFavorito.setUsuario(usuario);
			listaFavorito.setObjetos(new ArrayList<>());
			listaFavoritoNuevo = this.listaFavoritoService.guardarListaFavorito(listaFavorito);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("lista_favorito", listaFavoritoNuevo);
		response.put("mensaje", "La lista de favorito ha sido creada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	@PutMapping("/{listaFavorito_id}")
	public ResponseEntity<?> actualizarListaFavorito(@Valid @RequestBody ListaFavorito listaFavorito, @PathVariable Long listaFavorito_id, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		ListaFavorito listaFavoritoActualizado = null;
		ListaFavorito listaFavoritoRecuperado = null;
		
		try {
			listaFavoritoRecuperado = this.listaFavoritoService.findOne(listaFavorito_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(listaFavoritoRecuperado == null) {
			response.put("mensaje",	 "La lista favorito con id '".concat(listaFavorito_id.toString()).concat("' no existe."));
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
			listaFavoritoRecuperado.setNombre(listaFavorito.getNombre());
			listaFavoritoActualizado = this.listaFavoritoService.guardarListaFavorito(listaFavoritoRecuperado);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("listaFavorito", listaFavoritoActualizado);
		response.put("mensaje", "La lista de favorito ha sido actualizada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
			
	}
	
	
	@DeleteMapping("/{listaFavorito_id}")
	public ResponseEntity<?> eliminarListaFavorito(@PathVariable Long listaFavorito_id) {
		Map<String, Object> response = new HashMap<String, Object>();
		ListaFavorito listafavorito = null;
		
		try {
			listafavorito = this.listaFavoritoService.findOne(listaFavorito_id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		if(listafavorito == null) {
			response.put("mensaje",	 "La lista de favorito con id '".concat(listaFavorito_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			this.listaFavoritoService.eliminarListaFavorito(listafavorito);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "La lista de favorito ha sido borrada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);	
		
	}
	
	
	@GetMapping("/usuario/{usuario_id}")
	public ResponseEntity<?> listasFavoritoByUser(@PathVariable Long usuario_id) {
		List<ListaFavorito> listasFavorito = null;
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			usuario = this.usuarioService.findOne(usuario_id);
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
			listasFavorito = this.listaFavoritoService.listasFavoritoByUser(usuario_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		return new ResponseEntity<List<ListaFavorito>>(listasFavorito, HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/guardarObjeto")
	public ResponseEntity<?> guardarObjetoListaFavorito(@RequestParam Long listaFavorito_id, @RequestParam Long objeto_id) {
		ListaFavorito listaFavorito = null;
		Objeto objeto = null;
		ListaFavorito listaFavoritoNueva = new ListaFavorito();
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			listaFavorito = this.listaFavoritoService.findOne(listaFavorito_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(listaFavorito == null) {
			response.put("mensaje",	 "La lista de favorito con id '".concat(listaFavorito_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			objeto = this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objeto == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		if(listaFavorito.getObjetos().contains(objeto)){
			response.put("mensaje",	 "El objeto seleccionado ya se encuentra dentro de la lista ".concat("'" + listaFavorito.getNombre() + "'."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		
		try {			
			listaFavoritoNueva = this.listaFavoritoService.guardarObjetoListaFavorito(listaFavorito, objeto);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el guardado del objeto en la lista.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "Se ha guardado el objeto con éxito.");
		response.put("listaFavorito", listaFavoritoNueva);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/eliminarObjeto")
	public ResponseEntity<?> eliminarObjetoListaFavorito(@RequestParam Long listaFavorito_id, @RequestParam Long objeto_id) {
		ListaFavorito listaFavorito = null;
		Objeto objeto = null;
		ListaFavorito listaFavoritoNueva = new ListaFavorito();
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			listaFavorito = this.listaFavoritoService.findOne(listaFavorito_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(listaFavorito == null) {
			response.put("mensaje",	 "La lista de favorito con id '".concat(listaFavorito_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			objeto = this.objetoService.findOne(objeto_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(objeto == null) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		if(!listaFavorito.getObjetos().contains(objeto)) {
			response.put("mensaje",	 "El objeto con id '".concat(objeto_id.toString()).concat("' no se encuentra en la lista con id '".concat(listaFavorito_id.toString()).concat("'.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {			
			listaFavoritoNueva = this.listaFavoritoService.eliminarObjetoListaFavorito(listaFavorito, objeto);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el borrado del objeto en la lista.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "Se ha eliminado el objeto con éxito.");
		response.put("listaFavorito", listaFavoritoNueva);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	

}
