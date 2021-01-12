package com.compromiso.social.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.compromiso.social.modelo.Perfil;
import com.compromiso.social.modelo.Permiso;
import com.compromiso.social.modelo.Usuario;
import com.compromiso.social.modelo.dao.UsuarioDAO;


public class ServicioAutenticacion implements UserDetailsService {

	/**
	 * Este metodo es invocado en el momento de la autenticacion paraa recuperar 
	 * los datos del usuario.
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario empleado; 
			User usuarioSpring;
			List<GrantedAuthority> privilegios; 
			empleado = usuarioDAO.getUsuario(nombreUsuario);
			privilegios = obtienePrivilegios(empleado.getPerfil());
			
			usuarioSpring = new User(empleado.getUsuario(), empleado.getClave(), privilegios);

			return usuarioSpring;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		
	}

	/**
	 * Elabora una lista de objetos del tipo GrantedAuthority en base a los permisos
	 * del usuario.
	 * 
	 * @param usuario
	 * @return
	 */
	private List<GrantedAuthority> obtienePrivilegios(Perfil tipoUsuario) {
		List<GrantedAuthority> listaPrivilegios = new ArrayList<GrantedAuthority>(); 
		
	    for(Permiso permiso  : tipoUsuario.getPermisos())
	    	listaPrivilegios.add(new SimpleGrantedAuthority(permiso.getPerfil().getDescripcion()));

		return listaPrivilegios;
	}

}