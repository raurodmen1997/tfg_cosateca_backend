package com.cosateca.apirest.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.cosateca.apirest.enumerados.CategoriaHerramienta;

import validadores.ValueEnum;

@Entity(name = "objetos")
public class Objeto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Boolean accesible;

	@Column(nullable = false)
	@ValueEnum(enumClass = CategoriaHerramienta.class, message = "Debe contener alguno de estos valores: 'Montaje', 'Medición', 'Sujeción', 'Corte', 'Unión', 'Golpe'")
	private String categoria;

	@NotBlank
	@Column(nullable = false)
	private String descripcion;

	@NotBlank
	@Column(nullable = false)
	private String nombre;

	@Valid
	@OneToOne(optional = false)
	@JoinColumn(name = "imagen_id", nullable = false)
	private Imagen imagen;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "ayuntamiento_id", nullable = false)
	private Ayuntamiento ayuntamiento;

	
	@PrePersist
	public void prePersist() {
		this.accesible = true;
	}
	
	
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAccesible() {
		return accesible;
	}

	public void setAccesible(Boolean accesible) {
		this.accesible = accesible;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Ayuntamiento getAyuntamiento() {
		return ayuntamiento;
	}

	public void setAyuntamiento(Ayuntamiento ayuntamiento) {
		this.ayuntamiento = ayuntamiento;
	}

}
