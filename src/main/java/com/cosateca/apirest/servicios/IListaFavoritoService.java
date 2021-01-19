package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.ListaFavorito;

public interface IListaFavoritoService {

	ListaFavorito guardarListaFavorito(ListaFavorito lista);
	
	void eliminarListaFavorito(ListaFavorito listaFavorito);
	
	ListaFavorito findOne(Long lista_id);
	
	List<ListaFavorito> listasFavoritoByUser(Long id);
}
