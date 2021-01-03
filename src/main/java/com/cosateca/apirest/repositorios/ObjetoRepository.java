package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Objeto;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long>{

}
