package com.compromiso.social.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the simpatizante database table.
 * 
 */
@Entity
@NamedQuery(name="Simpatizante.findAll", query="SELECT s FROM Simpatizante s")
public class Simpatizante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_simpatizante")
	private Integer idSimpatizante;

	private String apellidos;

	private String cedula;

	private String celular;

	private String comuna;

	private String domicilio;

	private String email;

	private Boolean estado;

	private String nombres;

	//bi-directional many-to-one association to Parroquia
	@ManyToOne
	@JoinColumn(name="id_parroquia")
	private Parroquia parroquia;

	public Simpatizante() {
	}

	public Integer getIdSimpatizante() {
		return this.idSimpatizante;
	}

	public void setIdSimpatizante(Integer idSimpatizante) {
		this.idSimpatizante = idSimpatizante;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getComuna() {
		return this.comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Parroquia getParroquia() {
		return this.parroquia;
	}

	public void setParroquia(Parroquia parroquia) {
		this.parroquia = parroquia;
	}

}