package com.alghome.datagetset;

import java.io.Serializable;

public class GpsGetset implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -493799726556289160L;
	String tramaGps;
	String imei;
	String fechayhora;
	String latitud;
	String longitud;
	String velocidad;
	String altitud;
	String giro;
	String evento;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getFechayhora() {
		return fechayhora;
	}
	public void setFechayhora(String fechayhora) {
		this.fechayhora = fechayhora;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public String getAltitud() {
		return altitud;
	}
	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}
	public String getTramaGps() {
		return tramaGps;
	}
	public void setTramaGps(String tramaGps) {
		this.tramaGps = tramaGps;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	
	
}
