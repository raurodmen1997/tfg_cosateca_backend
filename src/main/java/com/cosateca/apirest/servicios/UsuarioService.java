package com.cosateca.apirest.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosateca.apirest.entidades.Usuario;
import com.cosateca.apirest.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService{

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ListaFavoritoService listaService;
	
	@Autowired
	private PeticionDonacionService peticionDonacionService;
	
	@Autowired
	private ValoracionService valoracionService;
	
	@Autowired
	private PeticionReservaService peticionReservaService;
	
	
	@Override
	public Usuario findUsuarioByCuenta(Long id) {
		return this.usuarioRepository.findUsuarioByCuenta(id);
	}


	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}


	@Override
	public Usuario findOne(Long id) {
		return this.usuarioRepository.findById(id).orElse(null);
	}


	/*
	@Override
	public List<Usuario> usuarioASerOlvidados() {
		return this.usuarioRepository.usuariosASerOlvidados();
	}
	*/


	@Override
	@Transactional
	public void eliminarUsuario(Usuario usuario) {
		this.valoracionService.eliminarValoracionesUsuario(usuario.getId());
		this.peticionDonacionService.eliminarPeticionesDonacionUsuario(usuario.getId());
		this.peticionReservaService.eliminarPeticionesReservaUsuario(usuario.getId());
		this.listaService.eliminarListasUsuario(usuario.getId());
		this.usuarioRepository.delete(usuario);
	}


	@Override
	public Page<Usuario> usuariosASerOlvidados(Pageable pageable) {
		return this.usuarioRepository.usuariosASerOlvidados(pageable);
	}


	@Override
	public List<Usuario> usuarioASerOlvidados() {
		return null;
	}

	
}
