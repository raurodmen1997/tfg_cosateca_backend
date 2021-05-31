package com.cosateca.apirest.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.Horario;
import com.cosateca.apirest.entidades.InfoAyuntamiento;
import com.cosateca.apirest.servicios.AyuntamientoService;
import com.cosateca.apirest.servicios.HorarioService;

@RestController
@RequestMapping("/api/entidades/ayuntamiento")
@CrossOrigin(origins = { "http://localhost:4200" })
public class AyuntamientoController {

	@Autowired
	private AyuntamientoService ayuntamientoService;
	
	@Autowired
	private HorarioService horarioService;
	
	@GetMapping("/obtenerInfo")
	public InfoAyuntamiento obtenerInfoAyuntamiento() {
		InfoAyuntamiento info = new InfoAyuntamiento();
		
		Ayuntamiento ayuntamiento = this.ayuntamientoService.obtenerAyuntamiento();
		List<Horario> horarios = this.horarioService.findAll();
		List<String> infoHorarios = new ArrayList<>();
		Map<String, List<String>> mapa = new HashMap<>();
		
		info.setCodigos_postales(ayuntamiento.getCodigos_postales());
		info.setDireccion_correo(ayuntamiento.getDireccion_email());
		info.setMunicipio(ayuntamiento.getMunicipio());
		info.setProvincia(ayuntamiento.getProvincia());
		info.setTelefono(ayuntamiento.getTelefono());
		info.setDireccion(ayuntamiento.getDireccion());
		
		for(Horario hor: horarios) {
			if(mapa.containsKey(hor.getDia())) {
				mapa.get(hor.getDia()).add(hor.getHora_apertura().toString().concat("-").concat(hor.getHora_cierre().toString()));
			}else {
				List<String> valor = new ArrayList<>();
				valor.add(hor.getHora_apertura().toString().concat("-").concat(hor.getHora_cierre().toString()));
				mapa.put(hor.getDia(), valor);
			}
		}
		
		for(String val:mapa.keySet()) {
			String linea = val.concat(" ");
			for(String fecha:mapa.get(val)) {
				linea = linea + fecha + " ";
			}
			infoHorarios.add(linea.trim());
		}
		
		info.setHorarios(infoHorarios);
		return info;
	}
	
	
}
