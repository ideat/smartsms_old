package com.mindware.domain;

public class Modem {
	private int modemId;
	private String identificador;
	private String puerto_com;
	private int baudRate;
	private String fabricante;
	private String modelo;
	
	public int getModemId() {
		return modemId;
	}
	public void setModemId(int modemId) {
		this.modemId = modemId;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getPuerto_com() {
		return puerto_com;
	}
	public void setPuerto_com(String puerto_com) {
		this.puerto_com = puerto_com;
	}
	public int getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	

}
