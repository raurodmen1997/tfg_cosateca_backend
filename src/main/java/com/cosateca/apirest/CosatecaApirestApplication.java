package com.cosateca.apirest;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cosateca.apirest.entidades.PeticionReserva;
import com.cosateca.apirest.enumerados.EstadoPeticionReserva;
import com.cosateca.apirest.servicios.PeticionReservaService;

@SpringBootApplication
public class CosatecaApirestApplication implements CommandLineRunner{


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private PeticionReservaService peticionReservaService;
	
	public static void main(String[] args) {
		SpringApplication.run(CosatecaApirestApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		String password = "12345";
		
		for (int i = 0; i < 4; i++) {
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}
		
	}
	
	
	
		@Scheduled(cron = "0 1 0 * * *")
		public void comprobarPeticionesReserva() {
			System.out.println("tarea");
			List<PeticionReserva> peticionesReservaEnProcesoDeRecogida = this.peticionReservaService.peticionesReservaEnProcesoDeRecogida();
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			System.out.println("Fecha actual: " + cal.getTime());
			for(PeticionReserva p : peticionesReservaEnProcesoDeRecogida) {
				if(p.getFecha_inicio_reserva().before(cal.getTime())) {
					p.setEstado(EstadoPeticionReserva.RECHAZADA.name());
					this.peticionReservaService.guardar(p);
				}
			}
		}
		
		@Configuration
		@EnableScheduling
		@ConditionalOnProperty(name="scheduling.enable", matchIfMissing=true)
		class SchedulingConfiguration{
			
		}

}
