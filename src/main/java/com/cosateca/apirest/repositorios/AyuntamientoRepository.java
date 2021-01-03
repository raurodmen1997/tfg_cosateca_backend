package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Ayuntamiento;

@Repository
public interface AyuntamientoRepository extends JpaRepository<Ayuntamiento, Long>{

	@Query("select a from ayuntamientos a where a.cuenta.id=?1")
	Ayuntamiento findAyuntamientoByCuenta(Long id);
	
}
