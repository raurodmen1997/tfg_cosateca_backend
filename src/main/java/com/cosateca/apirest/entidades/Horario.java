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

import com.cosateca.apirest.enumerados.DiaSemana;

import validadores.ValueEnum;

@Entity(name = "horarios")
public class Horario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date hora_apertura;

	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date hora_cierre;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "ayuntamiento_id", nullable = false)
	private Ayuntamiento ayuntamiento;

	@Column(nullable = false)
	@ValueEnum(enumClass = DiaSemana.class, message = "Debe contener alguno de estos valores: 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado' o 'Domingo'")
	private String dia;

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHora_apertura() {
		return hora_apertura;
	}

	public void setHora_apertura(Date hora_apertura) {
		this.hora_apertura = hora_apertura;
	}

	public Date getHora_cierre() {
		return hora_cierre;
	}

	public void setHora_cierre(Date hora_cierre) {
		this.hora_cierre = hora_cierre;
	}

	public Ayuntamiento getAyuntamiento() {
		return ayuntamiento;
	}

	public void setAyuntamiento(Ayuntamiento ayuntamiento) {
		this.ayuntamiento = ayuntamiento;
	}

}
