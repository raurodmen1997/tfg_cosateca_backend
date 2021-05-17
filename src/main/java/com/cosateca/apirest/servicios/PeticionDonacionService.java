package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public PeticionDonacion findOne(Long peticion_donacion_id) {
		return this.peticionDonacionRepository.findById(peticion_donacion_id).orElse(null);
	}

	@Override
	public void eliminarPeticionDonacion(PeticionDonacion peticionDonacion) {
		this.peticionDonacionRepository.delete(peticionDonacion);
		
	}

	@Override
	public void eliminarPeticionesDonacionUsuario(Long id) {
		this.peticionDonacionRepository.eliminarPeticionesDonacionUsuario(id);
		
	}

	@Override
	public Page<PeticionDonacion> findAllByPage(Pageable pageable) {
		return this.peticionDonacionRepository.findAll(pageable);
	}

	@Override
	public Page<PeticionDonacion> peticionesByUsuario(Long usuario_id, Pageable pageable) {
		return this.peticionDonacionRepository.peticionesByUsuario(usuario_id, pageable);
	}

	@Override
	public Page<PeticionDonacion> peticionesPendienteDeRevision(Pageable pageable) {
		return this.peticionDonacionRepository.peticionesPendienteDeRevision(pageable);
	}




}
