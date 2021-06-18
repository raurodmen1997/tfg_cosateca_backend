package com.cosateca.apirest.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("select u from usuarios u where u.cuenta.id=?1")
	Usuario findUsuarioByCuenta(Long id);
	
	@Query("select u from usuarios u where u.codigo_identificacion=?1")
	Usuario usuarioPorCodigoIdentificacion(String codigo_identificacion);
	
	/*
	@Query("select u from usuarios u where u.olvidado = 1")
	List<Usuario> usuariosASerOlvidados();
	*/
	
	@Query("select u from usuarios u where u.olvidado = 1")
	Page<Usuario> usuariosASerOlvidados(Pageable pageable);
}
