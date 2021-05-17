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
import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.cosateca.apirest.enumerados.TipoIdentificacion;

import validadores.ValueEnum;

@Entity(name = "usuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	@Pattern(regexp = "^\\d{5}$", message = "Debe ser un número de 5 dígitos.")
	private String codigo_postal;

	@NotBlank
	@Column(nullable = false)
	private String nombre;

	@NotBlank
	@Column(nullable = false)
	private String primer_apellido;

	@NotBlank
	@Column(nullable = false)
	private String segundo_apellido;

	@NotBlank
	@Column(nullable = false)
	private String telefono;

	@NotBlank
	@Email
	@Column(nullable = false, unique = true)
	private String direccion_email;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cuenta_id", nullable = false, unique = true)
	private Cuenta cuenta;

	@Column(nullable = false)
	@ValueEnum(enumClass = TipoIdentificacion.class, message = "Debe contener alguno de estos valores: 'NIF', 'NIE'")
	private String tipo_identificacion;

	@NotBlank
	@Column(nullable = false)
	@Pattern(regexp = "^\\d{8}[a-zA-Z]{1}$|^[XxTtYyZz]{1}[0-9]{7}[a-zA-Z]{1}$", message = "Debe contener un NIF o NIE válido.")
	private String codigo_identificacion;

	@Column(nullable = false)
	private Boolean olvidado;
	
	@PrePersist
	public void prePersist() {
		this.olvidado = false;
	}

	public Boolean getOlvidado() {
		return olvidado;
	}

	public void setOlvidado(Boolean olvidado) {
		this.olvidado = olvidado;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getTipo_identificacion() {
		return tipo_identificacion;
	}

	public void setTipo_identificacion(String tipo_identificacion) {
		this.tipo_identificacion = tipo_identificacion;
	}

	public String getCodigo_identificacion() {
		return codigo_identificacion;
	}

	public void setCodigo_identificacion(String codigo_identificacion) {
		this.codigo_identificacion = codigo_identificacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimer_apellido() {
		return primer_apellido;
	}

	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}

	public String getSegundo_apellido() {
		return segundo_apellido;
	}

	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
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

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

}
