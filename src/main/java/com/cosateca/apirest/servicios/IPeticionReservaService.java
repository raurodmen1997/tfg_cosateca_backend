package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cosateca.apirest.entidades.PeticionReserva;

public interface IPeticionReservaService {
	
	void eliminarPeticionesReservaUsuario(Long id);
	
	PeticionReserva guardar(PeticionReserva peticion);
	
	PeticionReserva findOne(Long peticion_id);
	
	Page<PeticionReserva> peticionesUsuario(Long usuario_id, Pageable pageable);
	
	Page<PeticionReserva> peticionesUsuarioCodigoIdentificacion(String codigo_identificacion, Pageable pageable);
	
	Page<PeticionReserva> findAllByPage(Pageable pageable);
	
	List<PeticionReserva> peticionesObjeto(Long objeto_id);
	
	List<PeticionReserva> peticionesReservaEnProcesoDeRecogida();

}
