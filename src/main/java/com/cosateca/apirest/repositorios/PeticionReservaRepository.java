package com.cosateca.apirest.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.PeticionReserva;

@Repository
public interface PeticionReservaRepository extends JpaRepository<PeticionReserva, Long>{

	@Modifying
	@Query("delete from peticiones_reservas p where p.usuario.id=?1")
	void eliminarPeticionesReservaUsuario(Long id);
	
	/*
	@Query("select p from peticiones_reservas p where p.usuario.id=?1")
	List<PeticionReserva> peticionesUsuario(Long usuario_id);
	*/
	
	@Query("select p from peticiones_reservas p where p.usuario.id=?1")
	Page<PeticionReserva> peticionesUsuario(Long usuario_id, Pageable pageable);
	
	@Query("select p from peticiones_reservas p where p.usuario.codigo_identificacion=?1")
	Page<PeticionReserva> peticionesUsuarioCodigoIdentificacion(String codigo_identificacion, Pageable pageable);
	
	@Query("select p from peticiones_reservas p where p.objeto.id=?1")
	List<PeticionReserva> peticionesObjeto(Long objeto_id);
	
	@Query("select p from peticiones_reservas p where p.estado='EN_PROCESO_DE_RECOGIDA'")
	List<PeticionReserva> peticionesReservaEnProcesoDeRecogida();
	
}
