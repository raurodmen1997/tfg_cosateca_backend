package com.cosateca.apirest.servicios;

import com.cosateca.apirest.entidades.Imagen;

public interface IImagenService {
	
	Imagen findImagenById(Long imagen_id);
	
	Imagen guardarImagen(Imagen imagen);

}
