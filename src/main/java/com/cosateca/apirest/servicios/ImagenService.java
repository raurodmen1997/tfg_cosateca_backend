package com.cosateca.apirest.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Imagen;
import com.cosateca.apirest.repositorios.ImagenRepository;

@Service
public class ImagenService implements IImagenService{

	@Autowired
	private ImagenRepository imagenRepository;

	@Override
	public Imagen guardarImagen(Imagen imagen) {	
		return this.imagenRepository.save(imagen);
	}

	@Override
	public Imagen findImagenById(Long imagen_id) {
		return this.imagenRepository.findById(imagen_id).orElse(null);
	}
}
