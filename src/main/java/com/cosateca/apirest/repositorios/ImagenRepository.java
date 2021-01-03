package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long>{

}
