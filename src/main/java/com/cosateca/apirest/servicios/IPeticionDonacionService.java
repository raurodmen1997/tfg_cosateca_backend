package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.PeticionDonacion;

public interface IPeticionDonacionService {
	
	List<PeticionDonacion> findAll();
	
	PeticionDonacion guardaPeticionDonacion(PeticionDonacion peticionDonacion);
	
	PeticionDonacion findOne(Long peticion_donacion_id);

}
