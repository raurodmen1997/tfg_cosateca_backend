package com.cosateca.apirest.servicios;

import com.cosateca.apirest.entidades.Ayuntamiento;

public interface IAyuntamientoService {

	Ayuntamiento findAyuntamientoByCuenta(Long id);
	
	Ayuntamiento obtenerAyuntamiento();
}
