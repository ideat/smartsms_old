package com.mindware.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contacto {

	private int contactoId;
	private String nombreContacto;
	private Date fechaImportacion;
	private String celular;
	private String campo1;
	private String campo2;
	private String campo3;
	private String estado;
	private Integer usuarioId;
	private List<Grupo> grupos = new ArrayList<>();


	public Contacto(){

	}

	public Contacto (int contactoId, String nombreContacto, Date fechaImportacion
			, String celular, String campo1, String campo2, String campo3, String estado, Integer usuarioId) {

		this.contactoId = contactoId;
		this.nombreContacto = nombreContacto;
		this.fechaImportacion = fechaImportacion;
		this.celular = celular;
		this.campo1 = campo1;
		this.campo2 = campo2;
		this.campo3 = campo3;
		this.estado = estado;
		this.usuarioId = usuarioId;
	}


	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public int getContactoId() {
		return contactoId;
	}
	public void setContactoId(int contactoId) {
		this.contactoId = contactoId;
	}
	public String getNombreContacto() {
		return nombreContacto;
	}
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Date getFechaImportacion() {
		return fechaImportacion;
	}
	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}
	public String getCelular() {
		return celular;
	}
	public void  setCelular(String celular) {
		this.celular = celular;
	}
	public String getCampo1() {
		return campo1;
	}
	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}
	public String getCampo2() {
		return campo2;
	}
	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}
	public String getCampo3() {
		return campo3;
	}
	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
