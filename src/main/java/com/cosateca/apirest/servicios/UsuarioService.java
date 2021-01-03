package com.cosateca.apirest.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService{

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public Usuario findUsuarioByCuenta(Long id) {
		return this.usuarioRepository.findUsuarioByCuenta(id);
	}

	
}
