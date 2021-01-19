package com.cosateca.apirest.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.cosateca.apirest.enumerados.Autoridad;

@Entity(name = "cuentas")
public class Cuenta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank
	@Column(nullable = false, unique = true)
	private String nombre_perfil;
	
	@NotBlank
	@Column(nullable = false)
	//@Pattern(regexp = "^?\\w*\\d?\\w*[A-Z]?\\w*[a-z]\\S{8,}$", message = "Debe contener, al menos, 8 caracteres, 1 letra minúscula, 1 letra mayúscula y 1 dígito.")
	private String pass;
	
	
	@Column(nullable = false)
	private Autoridad autoridad;
	
	

	public Autoridad getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(Autoridad autoridad) {
		this.autoridad = autoridad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_perfil() {
		return nombre_perfil;
	}

	public void setNombre_perfil(String nombre_perfil) {
		this.nombre_perfil = nombre_perfil;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
