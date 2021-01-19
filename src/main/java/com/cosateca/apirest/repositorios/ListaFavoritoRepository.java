package com.cosateca.apirest.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.ListaFavorito;

@Repository
public interface ListaFavoritoRepository extends JpaRepository<ListaFavorito, Long>{
	
	
	@Query("select l from listas_favoritos l where l.usuario.id=?1")
	List<ListaFavorito> listasFavoritoByUser(Long id); 

}
