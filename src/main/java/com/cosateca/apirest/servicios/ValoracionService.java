package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Valoracion;
import com.cosateca.apirest.repositorios.ValoracionRepository;

@Service
public class ValoracionService implements IValoracionService{

	
	@Autowired
	private ValoracionRepository valoracionRepository;

	@Override
	public Valoracion findOne(Long valoracion_id) {
		return this.valoracionRepository.findById(valoracion_id).orElse(null);
	}

	@Override
	public Valoracion guardarValoracion(Valoracion valoracion) {
		return this.valoracionRepository.save(valoracion);
	}

	@Override
	public void eliminarValoracion(Valoracion valoracion) {
		this.valoracionRepository.delete(valoracion);
	}

	@Override
	public void eliminarValoracionesUsuario(Long id) {
		this.valoracionRepository.eliminarValoracionesUsuario(id);
		
	}

	@Override
	public List<Valoracion> valoracionesObjeto(Long objeto_id) {
		return this.valoracionRepository.valoracionesObjeto(objeto_id);
	}

	@Override
	public Double valoracionMediaObjeto(Long objeto_id) {
		return this.valoracionRepository.valoracionMediaObjeto(objeto_id);
	}

	@Override
	public List<Valoracion> valoracionesUsuario(Long usuario_id) {
		return this.valoracionRepository.valoracionesUsuario(usuario_id);
	}
}
