package com.cosateca.apirest.entidades;

import java.util.List;

public class InfoAyuntamiento {
	
	private String municipio;
	private String provincia;
	private String telefono;
	private String direccion_correo;
	private String direccion;
	private String codigos_postales;
	private List<String> horarios;
	
	
	public InfoAyuntamiento() {
	}

	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion_correo() {
		return direccion_correo;
	}
	public void setDireccion_correo(String direccion_correo) {
		this.direccion_correo = direccion_correo;
	}
	
	public String getCodigos_postales() {
		return codigos_postales;
	}


	public void setCodigos_postales(String codigos_postales) {
		this.codigos_postales = codigos_postales;
	}


	public List<String> getHorarios() {
		return horarios;
	}
	public void setHorarios(List<String> horarios) {
		this.horarios = horarios;
	}
	
	

}
