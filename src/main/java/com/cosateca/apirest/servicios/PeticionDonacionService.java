package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.PeticionDonacion;
import com.cosateca.apirest.repositorios.PeticionDonacionRepository;

@Service
public class PeticionDonacionService implements IPeticionDonacionService{
	
	@Autowired
	private PeticionDonacionRepository peticionDonacionRepository;

	@Override
	public PeticionDonacion guardaPeticionDonacion(PeticionDonacion peticionDonacion) {
		return this.peticionDonacionRepository.save(peticionDonacion);
	}

	@Override
	public List<PeticionDonacion> findAll() {
		return this.peticionDonacionRepository.findAll();
	}

}
