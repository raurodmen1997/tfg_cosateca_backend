package com.cosateca.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.cosateca.apirest.entidades.Ayuntamiento;
import com.cosateca.apirest.entidades.Cuenta;
import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.enumerados.Autoridad;
import com.cosateca.apirest.servicios.AyuntamientoService;
import com.cosateca.apirest.servicios.CuentaService;
import com.cosateca.apirest.servicios.UsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AyuntamientoService ayuntamientoService;
	
	@Autowired
	private CuentaService cuentaService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Cuenta cuenta = this.cuentaService.findCuentaByUser(authentication.getName());
		Usuario usuario = null;
		Ayuntamiento ayuntamiento = null;
		if(cuenta.getAutoridad().equals(Autoridad.ROLE_ADMIN)) {
			ayuntamiento = this.ayuntamientoService.findAyuntamientoByCuenta(cuenta.getId());
		}else {
			usuario = this.usuarioService.findUsuarioByCuenta(cuenta.getId());
		}
		
		
		Map<String, Object> info = new HashMap<>();
		info.put("info_adicional", "Hola que tal!: ".concat(authentication.getName()));
		
		if(cuenta.getAutoridad().equals(Autoridad.ROLE_ADMIN)) {
			info.put("nombre", ayuntamiento.getNombre());
			info.put("municipio", ayuntamiento.getMunicipio());
			info.put("provincia", ayuntamiento.getProvincia());
			info.put("direccion_email", ayuntamiento.getDireccion_email());
			info.put("telefono", ayuntamiento.getTelefono());
			info.put("direccion", ayuntamiento.getDireccion());
			info.put("codigo_postal", ayuntamiento.getCodigos_postales());
			info.put("id", ayuntamiento.getId());
			info.put("id_cuenta", ayuntamiento.getCuenta().getId());
		}
		
		if(cuenta.getAutoridad().equals(Autoridad.ROLE_USER)) {
			info.put("nombre", usuario.getNombre());
			info.put("direccion_email", usuario.getDireccion_email());
			info.put("primer_apellido", usuario.getPrimer_apellido());
			info.put("segundo_apellido", usuario.getSegundo_apellido());
			info.put("telefono", usuario.getTelefono());
			info.put("id", usuario.getId());
			info.put("codigo_postal", usuario.getCodigo_postal());
			info.put("codigo_identificacion", usuario.getCodigo_identificacion());
			info.put("tipo_identificacion", usuario.getTipo_identificacion());
			info.put("olvidado", usuario.getOlvidado());
			info.put("id_cuenta", usuario.getCuenta().getId());
		}
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
	
}
