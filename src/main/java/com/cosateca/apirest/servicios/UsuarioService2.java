package com.cosateca.apirest.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.repositorios.CuentaRepository;

@Service
public class UsuarioService2 implements UserDetailsService{
	
	@Autowired
	private CuentaRepository cuentaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cuenta cuenta = this.cuentaRepository.findCuentaByUser(username);	
		if(cuenta == null) {
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema.");
		}	
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(cuenta);
		List<GrantedAuthority> autoridades = 
				cuentas.stream().map(x -> new SimpleGrantedAuthority(x.getAutoridad().name())).collect(Collectors.toList());
		
		return new User(username, cuenta.getPass(), true,true, true, true, autoridades);
	}

}
