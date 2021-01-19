package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.CarroCompra;

@Repository
public interface CarroCompraRepository extends JpaRepository<CarroCompra, Long>{

}
