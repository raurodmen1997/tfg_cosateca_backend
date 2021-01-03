package com.cosateca.apirest.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long>{

	@Query("select c from cuentas c where c.nombre_perfil=?1 and c.pass=?2")
	Cuenta findCuentaByUserAndPass(String perfil, String pass);
	
}
