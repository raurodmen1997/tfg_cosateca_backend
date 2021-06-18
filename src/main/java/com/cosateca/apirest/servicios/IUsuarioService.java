package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cosateca.apirest.entidades.Usuario;

public interface IUsuarioService {

	Usuario findUsuarioByCuenta(Long id);
	
	Page<Usuario> usuariosASerOlvidados(Pageable pageable);
	
	Usuario guardarUsuario(Usuario usuario);
	
	Usuario findOne(Long id);
	
	Usuario usuarioPorCodigoIdentificacion(String codigo_identificacion);
	
	List<Usuario> usuarioASerOlvidados();
	
	void eliminarUsuario(Usuario usuario);
} 
