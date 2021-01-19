package com.cosateca.apirest.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.CarroCompra;
import com.cosateca.apirest.repositorios.CarroCompraRepository;

@Service
public class CarroCompraService implements ICarroCompraService{

	@Autowired
	private CarroCompraRepository carroCompraRepository;

	@Override
	public CarroCompra guardarCarroCompra(CarroCompra carro_compra) {
		return this.carroCompraRepository.save(carro_compra);
	}
	
	
}
