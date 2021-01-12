package com.compromiso.social.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the parroquia database table.
 * 
 */
@Entity
@NamedQuery(name="Parroquia.findAll", query="SELECT p FROM Parroquia p")
public class Parroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parroquia")
	private Integer idParroquia;

	private Boolean estado;

	private String parroquia;

	//bi-directional many-to-one association to Canton
	@ManyToOne
	@JoinColumn(name="id_canton")
	private Canton canton;

	//bi-directional many-to-one association to Simpatizante
	@OneToMany(mappedBy="parroquia")
	private List<Simpatizante> simpatizantes;

	public Parroquia() {
	}

	public Integer getIdParroquia() {
		return this.idParroquia;
	}

	public void setIdParroquia(Integer idParroquia) {
		this.idParroquia = idParroquia;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getParroquia() {
		return this.parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public Canton getCanton() {
		return this.canton;
	}

	public void setCanton(Canton canton) {
		this.canton = canton;
	}

	public List<Simpatizante> getSimpatizantes() {
		return this.simpatizantes;
	}

	public void setSimpatizantes(List<Simpatizante> simpatizantes) {
		this.simpatizantes = simpatizantes;
	}

	public Simpatizante addSimpatizante(Simpatizante simpatizante) {
		getSimpatizantes().add(simpatizante);
		simpatizante.setParroquia(this);

		return simpatizante;
	}

	public Simpatizante removeSimpatizante(Simpatizante simpatizante) {
		getSimpatizantes().remove(simpatizante);
		simpatizante.setParroquia(null);

		return simpatizante;
	}

}