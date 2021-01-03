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

import com.cosateca.apirest.enumerados.EstadoPeticionDonacion;

@Entity(name = "peticiones_donaciones")
public class PeticionDonacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String categoria;

	@NotBlank
	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = false)
	private EstadoPeticionDonacion estado;

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

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	
	@PrePersist
	public void prePersist() {
		this.estado = EstadoPeticionDonacion.PENDIENTE_DE_REVISION;
	}

	public EstadoPeticionDonacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPeticionDonacion estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Ayuntamiento getAyuntamiento() {
		return ayuntamiento;
	}

	public void setAyuntamiento(Ayuntamiento ayuntamiento) {
		this.ayuntamiento = ayuntamiento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
