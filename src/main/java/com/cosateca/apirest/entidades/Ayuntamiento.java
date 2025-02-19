package com.cosateca.apirest.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Entity(name = "ayuntamientos")
public class Ayuntamiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String municipio;
	
	@NotBlank
	@Column(nullable = false)
	private String telefono;
	
	@NotBlank
	@Column(nullable = false)
	private String nombre;
	
	@NotBlank
	@Column(nullable = false)
	private String provincia;
	
	/*
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "ayuntamiento_id"))
	@Column(name = "codigo_postal")
	private List<Integer> codigos_postales;
	*/
	
	@NotBlank
	@Column(nullable = false)
	private String codigos_postales;
	
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cuenta_id",nullable = false, unique=true)
	private Cuenta cuenta;
	
	@NotBlank
	@Column(nullable = false)
	private String direccion_email;
	
	@NotBlank
	@Column(nullable = false)
	private String direccion;

	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion_email() {
		return direccion_email;
	}

	public void setDireccion_email(String direccion_email) {
		this.direccion_email = direccion_email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/*
	public List<Integer> getCodigos_postales() {
		return codigos_postales;
	}

	public void setCodigos_postales(List<Integer> codigos_postales) {
		this.codigos_postales = codigos_postales;
	}
	*/
	

	public Cuenta getCuenta() {
		return cuenta;
	}

	public String getCodigos_postales() {
		return codigos_postales;
	}

	public void setCodigos_postales(String codigos_postales) {
		this.codigos_postales = codigos_postales;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	

}
