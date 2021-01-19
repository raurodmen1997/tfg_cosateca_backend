package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.ListaFavorito;
import com.cosateca.apirest.repositorios.ListaFavoritoRepository;

@Service
public class ListaFavoritoService implements IListaFavoritoService{
	
	@Autowired
	private ListaFavoritoRepository listaFavoritoRepository;

	@Override
	public ListaFavorito guardarListaFavorito(ListaFavorito lista) {
		return this.listaFavoritoRepository.save(lista);
	}

	@Override
	public void eliminarListaFavorito(ListaFavorito listaFavorito) {
		this.listaFavoritoRepository.delete(listaFavorito);
	}

	@Override
	public ListaFavorito findOne(Long lista_id) {
		return this.listaFavoritoRepository.findById(lista_id).orElse(null);
	}

	@Override
	public List<ListaFavorito> listasFavoritoByUser(Long id) {
		return this.listaFavoritoRepository.listasFavoritoByUser(id);
	}

}
