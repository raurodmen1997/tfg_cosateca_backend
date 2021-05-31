package com.cosateca.apirest.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.PeticionReserva;
import com.cosateca.apirest.repositorios.PeticionReservaRepository;

@Service
public class PeticionReservaService implements IPeticionReservaService{

	@Autowired
	private PeticionReservaRepository peticionReservaRepository;
	
	@Override
	public void eliminarPeticionesReservaUsuario(Long id) {
		this.peticionReservaRepository.eliminarPeticionesReservaUsuario(id);
	}

	@Override
	public PeticionReserva guardar(PeticionReserva peticion) {
		return this.peticionReservaRepository.save(peticion);
	}

}
