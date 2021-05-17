package com.cosateca.apirest.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.cosateca.apirest.enumerados.CategoriaHerramienta;
import com.cosateca.apirest.enumerados.EstadoPeticionDonacion;

import validadores.ValueEnum;

@Entity(name = "peticiones_donaciones")
public class PeticionDonacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@ValueEnum(enumClass = CategoriaHerramienta.class, message = "Debe contener alguno de estos valores: 'Montaje', 'Medición', 'Sujeción', 'Corte', 'Unión', 'Golpe'")
	private String categoria;

	@NotBlank
	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = false)
	@ValueEnum(enumClass = EstadoPeticionDonacion.class, message = "Debe contener alguno de estos valores: 'PENDIENTE_DE_REVISION', 'ACEPTADA', 'RECHAZADA'")
	private String estado;

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
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fecha_creacion;

	

	@PrePersist
	public void prePersist() {
		this.estado = EstadoPeticionDonacion.PENDIENTE_DE_REVISION.name();
		this.fecha_creacion = new Date();
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
}
