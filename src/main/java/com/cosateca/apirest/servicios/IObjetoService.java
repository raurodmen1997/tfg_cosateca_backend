package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.Objeto;

public interface IObjetoService{
	
	List<Objeto> findAllObjetos();
	
	Objeto guardarObjeto(Objeto objeto);

}
