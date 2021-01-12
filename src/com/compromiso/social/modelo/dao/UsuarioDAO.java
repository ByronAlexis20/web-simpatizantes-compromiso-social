package com.compromiso.social.modelo.dao;

import javax.persistence.Query;

import com.compromiso.social.modelo.Usuario;

public class UsuarioDAO extends ClaseDAO {
	public Usuario getUsuario(String nombreUsuario) {
		Usuario usuario; 
		Query consulta;
		consulta = getEntityManager().createNamedQuery("Usuario.buscaUsuario");
		consulta.setParameter("nombreUsuario", nombreUsuario);
		usuario = (Usuario) consulta.getSingleResult();
		return usuario;
	}
}
