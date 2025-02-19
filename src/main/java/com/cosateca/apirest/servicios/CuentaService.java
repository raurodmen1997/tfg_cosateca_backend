package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.repositorios.CuentaRepository;

@Service
public class CuentaService implements ICuentaService{
	
	@Autowired
	private CuentaRepository cuentaRepository;

	@Override
	public Cuenta findCuentaByUserAndPass(String perfil, String pass) {
		return this.cuentaRepository.findCuentaByUserAndPass(perfil, pass);
	}

	@Override
	public List<Cuenta> findAll() {
		return this.cuentaRepository.findAll();
	}

	@Override
	public Cuenta guardarCuenta(Cuenta cuenta) {
		return this.cuentaRepository.save(cuenta);
	}

	@Override
	public Cuenta findCuentaByUser(String perfil) {
		return this.cuentaRepository.findCuentaByUser(perfil);
	}

	@Override
	public Cuenta findOne(Long cuenta_id) {
		return this.cuentaRepository.findById(cuenta_id).orElse(null);
	}

}
