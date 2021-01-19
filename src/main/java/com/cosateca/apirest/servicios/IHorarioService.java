package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cosateca.apirest.entidades.Horario;

public interface IHorarioService {
	
	List<Horario> findAll();
	
	Page<Horario> findAllByPage(Pageable pageable);
	
	Horario guardarHorario(Horario horario);
	
	Horario findOne(Long horario_id);
	
	void delete(Horario horario);

}
