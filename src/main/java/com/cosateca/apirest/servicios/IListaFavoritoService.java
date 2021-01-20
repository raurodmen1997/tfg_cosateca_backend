package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.ListaFavorito;
import com.cosateca.apirest.entidades.Objeto;

public interface IListaFavoritoService {

	ListaFavorito guardarListaFavorito(ListaFavorito lista);
	
	void eliminarListaFavorito(ListaFavorito listaFavorito);
	
	ListaFavorito findOne(Long lista_id);
	
	List<ListaFavorito> listasFavoritoByUser(Long id);
	
	ListaFavorito guardarObjetoListaFavorito(ListaFavorito lista, Objeto objeto);
	
	ListaFavorito eliminarObjetoListaFavorito(ListaFavorito lista, Objeto objeto);
}
