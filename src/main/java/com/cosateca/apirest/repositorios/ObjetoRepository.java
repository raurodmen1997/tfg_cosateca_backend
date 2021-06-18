package com.cosateca.apirest.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosateca.apirest.entidades.Objeto;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long>{
	
	@Query("select o from objetos o where o.accesible=true")
	List<Objeto> objetosAccesibles();
	
	@Query("select o from objetos o where o.accesible=false")
	List<Objeto> objetosInaccesibles();
	
	@Query("select o from objetos o where o.categoria=?1 and o.accesible=1")
	List<Objeto> objetosPorCateogiraAccesible(String categoria);
	
	@Query("select o from objetos o where o.categoria=?1 and o.accesible=?2")
	List<Objeto> objetosPorCateogiraYAccesibilidad(String categoria, Boolean accesible);
	
	@Query("select o from objetos o where o.categoria=?1")
	List<Objeto> objetosPorCateogiraAdmin(String categoria);

}
