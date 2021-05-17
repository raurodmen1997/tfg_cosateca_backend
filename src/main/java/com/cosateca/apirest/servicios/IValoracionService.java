package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.Valoracion;

public interface IValoracionService {

	Valoracion findOne(Long valoracion_id);
	
	Valoracion guardarValoracion(Valoracion valoracion);
	
	void eliminarValoracion(Valoracion valoracion);
	
	void eliminarValoracionesUsuario(Long id);
	
	List<Valoracion> valoracionesObjeto(Long objeto_id);
	
	List<Valoracion> valoracionesUsuario(Long usuario_id);
	
	Double valoracionMediaObjeto(Long objeto_id);
}
