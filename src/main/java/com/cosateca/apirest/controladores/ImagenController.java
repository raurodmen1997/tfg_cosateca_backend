package com.cosateca.apirest.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cosateca.apirest.entidades.Imagen;
import com.cosateca.apirest.servicios.ImagenService;

@RestController
@RequestMapping("/api/entidades/imagenes")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ImagenController {
	
	
	@Autowired
	private ImagenService imagenService;
	
	
	@GetMapping(value = "{imagen_id}", produces = {MediaType.IMAGE_JPEG_VALUE})
	public ResponseEntity<?> obtenerImagen(@PathVariable Long imagen_id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Imagen imagen = null;
		
		try {
			imagen = this.imagenService.findImagenById(imagen_id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(imagen == null) {
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.add(HttpHeaders.CONTENT_TYPE, "application/json");
			response.put("mensaje", "La imagen con id '".concat(imagen_id.toString()).concat("' no existe."));
			return new ResponseEntity<Map<String, Object>>(response, cabecera, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<byte[]>(imagen.getBytes(),HttpStatus.OK); 
			
	}

	
	@PostMapping("")
	public ResponseEntity<?> crearImagen(@RequestParam("archivo") MultipartFile archivo ) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Imagen imagen = new Imagen();
		Imagen imagenNueva = null;
		
		byte[] bytes = archivo.getBytes();
		if(bytes == null) {
			response.put("mensaje", "El archivo no contiene datos.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		imagen.setBytes(bytes);
		
		try {
			imagenNueva = this.imagenService.guardarImagen(imagen);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<Imagen>(imagenNueva,HttpStatus.CREATED); 
			
	}
}
