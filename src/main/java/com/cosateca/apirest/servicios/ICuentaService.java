package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.Cuenta;

public interface ICuentaService {

	Cuenta findCuentaByUserAndPass(String perfil, String pass);
	
	Cuenta findCuentaByUser(String perfil);
	
	Cuenta findOne(Long cuenta_id);
	
	List<Cuenta> findAll();
	
	Cuenta guardarCuenta(Cuenta cuenta);
}
