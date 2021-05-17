package com.cosateca.apirest.repositorios;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.PeticionDonacion;

@Repository
public interface PeticionDonacionRepository extends JpaRepository<PeticionDonacion, Long>{

	@Modifying
	@Query("delete from peticiones_donaciones p where p.usuario.id=?1")
	void eliminarPeticionesDonacionUsuario(Long id);
	
	@Query("select p from peticiones_donaciones p where p.usuario.id=?1")
	Page<PeticionDonacion> peticionesByUsuario(Long usuario, Pageable pageable);
	
	@Query("select p from peticiones_donaciones p where p.estado='PENDIENTE_DE_REVISION'")
	Page<PeticionDonacion> peticionesPendienteDeRevision(Pageable pageable);
}
