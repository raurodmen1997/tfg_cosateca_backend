package com.cosateca.apirest.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long>{

	@Modifying
	@Query("delete from valoraciones v where v.usuario.id=?1")
	void eliminarValoracionesUsuario(Long id);
	
	
	@Query("select v from valoraciones v where v.objeto.id=?1")
	List<Valoracion> valoracionesObjeto(Long objeto_id);
	
	
	@Query("select avg(v.puntuacion) from valoraciones v where v.objeto.id = ?1") 
	Double valoracionMediaObjeto(Long objeto_id);
	
	
	@Query("select v from valoraciones v where v.usuario.id=?1")
	List<Valoracion> valoracionesUsuario(Long usuario_id);
	
}
