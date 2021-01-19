package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.Cuenta;

public interface ICuentaService {

	Cuenta findCuentaByUserAndPass(String perfil, String pass);
	
	List<Cuenta> findAll();
	
	Cuenta guardarCuenta(Cuenta cuenta);
}
