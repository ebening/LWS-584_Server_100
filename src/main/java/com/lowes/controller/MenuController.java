package com.lowes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lowes.entity.Menu;
import com.lowes.service.MenuService;

/**
 * @author Josue Sanchez
 * @version 1.0
 */

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class MenuController {

	
	public MenuController(){
		System.out.println("MenuController");
	}
	
	@Autowired
	private MenuService menuService;
	
	
	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/createMenu", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public @ResponseBody ResponseEntity<String> createMenu(HttpSession session, 
			HttpServletRequest request, HttpServletResponse response) {

		StringBuilder html = new StringBuilder();
		// extraer menu de la tabla
		List<Menu> lstMenu =  menuService.getAllMenu();
		
		// prevent null y vacios
		if(lstMenu != null && lstMenu.isEmpty() == false){
			
			//extraer todos los nodos padre
			List<Menu> lstNodosPadre = new ArrayList<>();
			for(Menu lst : lstMenu){
		       if(lst.getEsNodo() == 0){
		    	   lstNodosPadre.add(lst);
		       }		
			}
			
			
			if(lstNodosPadre.size() > 0){
				for(Menu lstNPadre : lstNodosPadre){
					html.append("<ul>");
					 html.append("<li>");
					   html.append("<input class=\"check\" id=\""+lstNPadre.getIdMenu()+"\" type=\"checkbox\"><span>"+lstNPadre.getNombre()+"</span>");
					     List<Menu> hijos = getHijos(lstNPadre.getIdMenu(),lstMenu);
					     if(hijos != null && hijos.isEmpty() == false){
					    	   for(Menu h : hijos){
						    	 html.append("<ul>");
										 html.append("<li>");
										   html.append("<input class=\"check\" id=\""+h.getIdMenu()+"\" type=\"checkbox\"><span>"+h.getNombre()+"</span>");
										   List<Menu> nietos = getHijos(h.getIdMenu(),lstMenu);
										   if(nietos != null && nietos.isEmpty() == false){
										    	 html.append("<ul>");
										    	    for(Menu n : nietos){
										    	    	html.append("<li><input class=\"check\"  id=\""+n.getIdMenu()+"\" type=\"checkbox\"><span>"+n.getNombre()+"</span></li>");
										    	    }
										    	 html.append("</ul>");
										   }
										 html.append("</li>");
						    	 html.append("</ul>");
					    	  }
					     }
					 html.append("</li>");
					html.append("</ul>");
				}
			}
			
			
		}
		

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(html.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	// metodo ajax para la carga dinamica de edicion.
		@RequestMapping(value = "/createSideMenu", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
		public @ResponseBody ResponseEntity<String> createMenuLateral(HttpSession session, 
				HttpServletRequest request, HttpServletResponse response) {
			
			
			StringBuilder menu = new StringBuilder();
			
	    menu.append("<li class=\"\">");
			  menu.append("<a href=\"#\"><i class=\"fa fa-sitemap fa-fw\"></i>").append("Multi-Level Dropdown").append("<span class=\"fa arrow\"></span></a>");
			  
			  menu.append("<ul class=\"nav nav-second-level collapse\" aria-expanded=\"false\" style=\"height: 0px;\">");
			     menu.append("<li><a href=\"#\">Second Level Item</a></li>");
			     menu.append("<li class=\"\">");
			     menu.append("<a href=\"#\">Third Level <span class=\"fa arrow\"></span></a>");
			      menu.append("<ul class=\"nav nav-third-level collapse\" aria-expanded=\"false\" style=\"height: 0px;\">");
			        menu.append("<li><a href=\"#\">Third Level Item</a></li>");
			      menu.append("</ul>");
                 //   <!-- /.nav-third-level -->
			     menu.append("</li>");
			  menu.append("</ul>");
            //<!-- /.nav-second-level -->
		menu.append("</li>");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(menu.toString(), responseHeaders, HttpStatus.CREATED);
		}
	
	
	
	
	
	private List<Menu> getHijos(Integer id, List<Menu> setMenu){
		
		List<Menu> childrenAux = new ArrayList<>();
		
		for(Menu menu : setMenu){
			if(menu.getMenuPadre() != null && menu.getMenuPadre().getIdMenu() == id){
				childrenAux.add(menu);
			}
		}
		return childrenAux;
	}
	
	
	
}

