package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<PeticionReserva> peticionesUsuario(Long usuario_id, Pageable pageable) {
		return peticionReservaRepository.peticionesUsuario(usuario_id, pageable);
	}

	@Override
	public Page<PeticionReserva> findAllByPage(Pageable pageable) {
		return this.peticionReservaRepository.findAll(pageable);
	}

	@Override
	public PeticionReserva findOne(Long peticion_id) {
		return this.peticionReservaRepository.findById(peticion_id).orElse(null);
	}

	@Override
	public List<PeticionReserva> peticionesObjeto(Long objeto_id) {
		return this.peticionReservaRepository.peticionesObjeto(objeto_id);
	}

	@Override
	public Page<PeticionReserva> peticionesUsuarioCodigoIdentificacion(String codigo_identificacion,
			Pageable pageable) {
		return this.peticionReservaRepository.peticionesUsuarioCodigoIdentificacion(codigo_identificacion, pageable);
	}

	@Override
	public List<PeticionReserva> peticionesReservaEnProcesoDeRecogida() {
		return this.peticionReservaRepository.peticionesReservaEnProcesoDeRecogida();
	}

}
