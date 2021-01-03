package com.cosateca.apirest.servicios;

import java.util.List;

import com.cosateca.apirest.entidades.Horario;

public interface IHorarioService {
	
	List<Horario> findAll();
	
	Horario guardarHorario(Horario horario);
	
	Horario findOne(Long horario_id);
	
	void delete(Horario horario);

}
