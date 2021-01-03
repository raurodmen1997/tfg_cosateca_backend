package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Horario;
import com.cosateca.apirest.repositorios.HorarioRepository;

@Service
public class HorarioService implements IHorarioService{
	
	@Autowired
	private HorarioRepository horarioRepository;

	@Override
	public List<Horario> findAll() {
		return this.horarioRepository.findAll();
	}

	@Override
	public Horario guardarHorario(Horario horario) {
		return this.horarioRepository.save(horario);
	}

	@Override
	public Horario findOne(Long horario_id) {
		return this.horarioRepository.findById(horario_id).orElse(null);
	}

	@Override
	public void delete(Horario horario) {
		this.horarioRepository.delete(horario);
		
	}

	
}
