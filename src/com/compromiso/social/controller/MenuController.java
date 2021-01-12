package com.compromiso.social.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.mail.MessagingException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.compromiso.social.modelo.Menu;
import com.compromiso.social.modelo.Permiso;
import com.compromiso.social.modelo.Usuario;
import com.compromiso.social.modelo.dao.MenuDAO;
import com.compromiso.social.modelo.dao.PermisoDAO;
import com.compromiso.social.modelo.dao.UsuarioDAO;
import com.compromiso.social.util.SecurityUtil;

public class MenuController {
	@Wire Tree menu;
	@Wire Include areaContenido;
	Menu opcionSeleccionado;
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	PermisoDAO permisoDAO = new PermisoDAO();
	MenuDAO menuDAO = new MenuDAO();
	List<Menu> listaPermisosPadre = new ArrayList<Menu>();
	List<Permiso> listaPermisosHijo = new ArrayList<Permiso>();
	
	@AfterCompose
	public void aferCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException, MessagingException{
		Selectors.wireComponents(view, this, false);
		loadTree();
		//startLongOperation();
	}
	
	public void loadTree() throws IOException{		
		Usuario usuario = usuarioDAO.getUsuario(SecurityUtil.getUser().getUsername().trim()); 
		if (usuario != null){
			//listaPermisosPadre = permisoDAO.getListaPermisosPadre(usuario.getSegPerfil().getIdPerfil());
			listaPermisosHijo = permisoDAO.getListaPermisosHijo(usuario.getPerfil().getIdPerfil());
			
			//obtener la lista de menus padre de cada menu
			boolean bandera = false;
			for(Permiso per : listaPermisosHijo) {
				bandera = false;
				List<Menu> listaMenu = menuDAO.getMenuPadre(per.getMenu().getIdMenuPadre());
				if(listaMenu.size() > 0) {
					for(Menu mnu : listaPermisosPadre) {
						for(Menu mnu2 : listaMenu) {
							if(mnu.getIdMenu() == mnu2.getIdMenu())
								bandera = true;
						}
					}
					if(bandera == false)
						listaPermisosPadre.add(listaMenu.get(0));
				}
			}
			
			if (listaPermisosPadre.size() > 0) { //si tiene permisos el usuario
				//capturar solo los menus
				List<Menu> listaMenu = new ArrayList<Menu>();
				System.out.println("Padres");
				for(Menu permiso : listaPermisosPadre) {
					listaMenu.add(permiso);
				}
				//ordenar el menu por posiciones
				System.out.println("Menu ordenado"); 
				Collections.sort(listaMenu, new Comparator<Menu>() {
					@Override
					public int compare(Menu p1, Menu p2) {
						return new Integer(p1.getPosicion()).compareTo(new Integer(p2.getPosicion()));
					}
				});
				menu.appendChild(getTreechildren(listaMenu));   
			}			
		}
	}
	private Treechildren getTreechildren(List<Menu> listaMenu) {
		Treechildren retorno = new Treechildren();
		for(Menu opcion : listaMenu) {
			Treeitem ti = getTreeitem(opcion);
			retorno.appendChild(ti);
			List<Menu> listaPadreHijo = new ArrayList<Menu>();
			for(Permiso permiso : listaPermisosHijo) {
				if(permiso.getMenu().getIdMenuPadre() == opcion.getIdMenu()) {
					listaPadreHijo.add(permiso.getMenu());
				}
			}
			if (!listaPadreHijo.isEmpty()) {
				Collections.sort(listaPadreHijo, new Comparator<Menu>() {
					@Override
					public int compare(Menu p1, Menu p2) {
						return new Integer(p1.getPosicion()).compareTo(new Integer(p2.getPosicion()));
					}
				});
				ti.appendChild(getTreechildren(listaPadreHijo));
			}
		}
		return retorno;
	}
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	private Treeitem getTreeitem(Menu opcion) {
		Treeitem retorno = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tc = new Treecell(opcion.getDescripcion());
		//System.out.println("titulomenu: " + tc);
		if (opcion.getIcono() != null) {
			//tc.setIconSclass(opcion.getImagen());
			tc.setSrc(opcion.getIcono());
		}
		tr.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				opcionSeleccionado = opcion; 
				if(opcionSeleccionado.getFormulario() != null){
					loadContenido(opcionSeleccionado);
				}
			}
		});
		
		tr.appendChild(tc);
		retorno.appendChild(tr);
		retorno.setOpen(false);
		return retorno;
	}
	@NotifyChange({"areaContenido"})
	public void loadContenido(Menu opcion) {
		
		if (opcion.getFormulario().toLowerCase().substring(0, 2).toLowerCase().equals("http")) {
			Sessions.getCurrent().setAttribute("FormularioActual", null);
			Executions.getCurrent().sendRedirect(opcion.getFormulario(), "_blank");			
		} else {
			Sessions.getCurrent().setAttribute("FormularioActual", opcion);	
			areaContenido.setSrc(opcion.getFormulario());
		}	
		
	}
	public String getNombreUsuario() {
		return SecurityUtil.getUser().getUsername();
	}
}
