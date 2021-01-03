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

}
