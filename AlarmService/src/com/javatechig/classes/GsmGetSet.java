package com.javatechig.classes;

import java.io.Serializable;

public class GsmGetSet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1783524690166269005L;
	String imei;
	String opName;
	String mcc;
	String mnc;
	String lac;
	String cellId;
	String time;
	String tramaGsm;
	String evento;
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMnc() {
		return mnc;
	}
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
		this.lac = lac;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getTramaGsm() {
		return tramaGsm;
	}
	public void setTramaGsm(String tramaGsm) {
		this.tramaGsm = tramaGsm;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	

}
