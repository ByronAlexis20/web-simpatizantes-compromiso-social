package com.compromiso.social.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(name="menu")
@NamedQueries({
	@NamedQuery(name="Menu.findAll", query="SELECT s FROM Menu s where s.estado='A' order by s.posicion asc"),
	@NamedQuery(name="Menu.buscarHijos", query="SELECT s FROM Menu s where s.estado = 'A' and s.idMenuPadre = :idPadre order by s.posicion asc"),
	@NamedQuery(name="Menu.buscarListadoHijos", query="SELECT s FROM Menu s where s.estado='A' and s.idMenuPadre <> 0"),
	@NamedQuery(name="Menu.menuPadre", query="SELECT s FROM Menu s where s.estado='A' and s.idMenu = :idMenuPadre"),
})
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menu")
	private Integer idMenu;

	private String descripcion;

	private Boolean estado;

	private String formulario;
	
	private String icono;

	@Column(name="id_menu_padre")
	private Integer idMenuPadre;

	private Integer posicion;

	//bi-directional many-to-one association to Permiso
	@OneToMany(mappedBy="menu")
	private List<Permiso> permisos;

	public Menu() {
	}

	public Integer getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getFormulario() {
		return this.formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	public Integer getIdMenuPadre() {
		return this.idMenuPadre;
	}

	public void setIdMenuPadre(Integer idMenuPadre) {
		this.idMenuPadre = idMenuPadre;
	}

	public Integer getPosicion() {
		return this.posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public List<Permiso> getPermisos() {
		return this.permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	public Permiso addPermiso(Permiso permiso) {
		getPermisos().add(permiso);
		permiso.setMenu(this);

		return permiso;
	}

	public Permiso removePermiso(Permiso permiso) {
		getPermisos().remove(permiso);
		permiso.setMenu(null);

		return permiso;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

}