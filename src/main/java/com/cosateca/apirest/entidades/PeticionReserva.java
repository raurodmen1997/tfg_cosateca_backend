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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import com.cosateca.apirest.enumerados.EstadoPeticionReserva;

@Entity(name = "peticion_reserva")
public class PeticionReserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private EstadoPeticionReserva estado;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
	private Date fecha_fin_reserva;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
	private Date fecha_inicio_reserva;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "ayuntamiento_id", nullable = false)
	private Ayuntamiento ayuntamiento;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "objeto_id", nullable = false)
	private Objeto objeto;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "carro_compra_id", nullable = false)
	private CarroCompra carro_compra;

	public EstadoPeticionReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoPeticionReserva estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha_fin_reserva() {
		return fecha_fin_reserva;
	}

	public void setFecha_fin_reserva(Date fecha_fin_reserva) {
		this.fecha_fin_reserva = fecha_fin_reserva;
	}

	public Date getFecha_inicio_reserva() {
		return fecha_inicio_reserva;
	}

	public void setFecha_inicio_reserva(Date fecha_inicio_reserva) {
		this.fecha_inicio_reserva = fecha_inicio_reserva;
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

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public CarroCompra getCarro_compra() {
		return carro_compra;
	}

	public void setCarro_compra(CarroCompra carro_compra) {
		this.carro_compra = carro_compra;
	}

}
