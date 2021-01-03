package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.PeticionDonacion;

@Repository
public interface PeticionDonacionRepository extends JpaRepository<PeticionDonacion, Long>{

}
