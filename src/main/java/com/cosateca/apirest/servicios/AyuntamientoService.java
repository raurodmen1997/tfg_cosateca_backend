package com.cosateca.apirest.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.repositorios.AyuntamientoRepository;

@Service
public class AyuntamientoService implements IAyuntamientoService{
	
	
	@Autowired
	private AyuntamientoRepository ayuntamientoRepository;

	@Override
	@Transactional(readOnly = true)
	public Ayuntamiento findAyuntamientoByCuenta(Long id) {
		return this.ayuntamientoRepository.findAyuntamientoByCuenta(id);
	}

	@Override
	public Ayuntamiento obtenerAyuntamiento() {
		return this.ayuntamientoRepository.findAll().get(0);
	}

	@Override
	public Ayuntamiento findOne(Long ayuntamiento_id) {
		return this.ayuntamientoRepository.findById(ayuntamiento_id).orElse(null);
	}

	@Override
	public Ayuntamiento guardarAyuntameinto(Ayuntamiento ayuntamiento) {
		return this.ayuntamientoRepository.save(ayuntamiento);
	}

}
