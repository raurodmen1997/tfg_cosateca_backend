package com.cosateca.apirest.servicios;

import com.cosateca.apirest.entidades.Usuario;

public interface IUsuarioService {

	Usuario findUsuarioByCuenta(Long id);
	
	Usuario guardarUsuario(Usuario usuario);
	
	Usuario findOne(Long id);
}
