package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Objeto;
import com.cosateca.apirest.repositorios.ObjetoRepository;

@Service
public class ObjetoService implements IObjetoService{
	
	@Autowired
	private ObjetoRepository objetoRepository;

	@Override
	public Objeto guardarObjeto(Objeto objeto) {
		return this.objetoRepository.save(objeto);
	}

	@Override
	public List<Objeto> findAllObjetos() {
		return this.objetoRepository.findAll();
	}

	@Override
	public Objeto findOne(Long objeto_id) {
		return this.objetoRepository.findById(objeto_id).orElse(null);
	}

	@Override
	public List<Objeto> objetosAccesibles() {
		return this.objetoRepository.objetosAccesibles();
	}

	@Override
	public List<Objeto> objetosPorCategoriaAccesible(String categoria) {
		return this.objetoRepository.objetosPorCateogiraAccesible(categoria);
	}

	@Override
	public List<Objeto> objetosPorCategoriaAdmin(String categoria) {
		return this.objetoRepository.objetosPorCateogiraAdmin(categoria);
	}

	@Override
	public List<Objeto> objetosPorCategoriaAccesibleyAccesibilidad(String categoria, Boolean accesible) {
		return this.objetoRepository.objetosPorCateogiraYAccesibilidad(categoria, accesible);
	}

	@Override
	public List<Objeto> objetosInaccesibles() {
		return this.objetoRepository.objetosInaccesibles();
	}

}
