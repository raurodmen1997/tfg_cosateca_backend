package com.cosateca.apirest.servicios;

import com.cosateca.apirest.entidades.PeticionReserva;

public interface IPeticionReservaService {
	
	void eliminarPeticionesReservaUsuario(Long id);
	
	PeticionReserva guardar(PeticionReserva peticion);

}
