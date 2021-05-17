package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.PeticionReserva;

@Repository
public interface PeticionReservaRepository extends JpaRepository<PeticionReserva, Long>{

	@Modifying
	@Query("delete from peticion_reserva p where p.usuario.id=?1")
	void eliminarPeticionesReservaUsuario(Long id);
}
