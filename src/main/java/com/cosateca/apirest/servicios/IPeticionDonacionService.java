package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cosateca.apirest.entidades.PeticionDonacion;

public interface IPeticionDonacionService {
	
	List<PeticionDonacion> findAll();
	
	Page<PeticionDonacion> findAllByPage(Pageable pageable);
	
	Page<PeticionDonacion> peticionesByUsuario(Long usuario_id, Pageable pageable);
	
	Page<PeticionDonacion> peticionesPendienteDeRevision(Pageable pageable);
	
	PeticionDonacion guardaPeticionDonacion(PeticionDonacion peticionDonacion);
	
	PeticionDonacion findOne(Long peticion_donacion_id);
	
	void eliminarPeticionDonacion(PeticionDonacion peticionDonacion);

	void eliminarPeticionesDonacionUsuario(Long id);
}
