package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.Objeto;

public interface IObjetoService{
	
	List<Objeto> findAllObjetos();
	
	Objeto guardarObjeto(Objeto objeto);
	
	Objeto findOne(Long objeto_id);
	
	List<Objeto> objetosAccesibles();
	
	List<Objeto> objetosInaccesibles();
	
	List<Objeto> objetosPorCategoriaAccesible(String categoria);
	
	List<Objeto> objetosPorCategoriaAccesibleyAccesibilidad(String categoria, Boolean accesible);
	
	List<Objeto> objetosPorCategoriaAdmin(String categoria);

}
