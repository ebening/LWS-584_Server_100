package com.lowes.handler;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lowes.entity.Menu;
import com.lowes.entity.Usuario;
import com.lowes.service.MenuService;
import com.lowes.service.UsuarioService;

public class RequestProcessingInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MenuService menuService;

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			
			// declaracion del modelo de objetos para el menu.
			HashMap<String, Object> model = new HashMap<String, Object>();
			
			// obtenemos el usuario en sesion.
			Usuario usuario = usuarioService.getUsuarioSesion();
				if(usuario.getFotoPerfil() == null || usuario.getFotoPerfil().isEmpty()){
				usuario.setFotoPerfil("default.png");
			}
			
			// se obtiene el objeto del menu
			List<Menu> menuUsuario = menuService.getMenuByPerfil(usuario.getPerfil().getIdPerfil());
			
			model.put("menuList", menuUsuario);
			model.put("usuario", usuario);

			modelAndView.addObject("model", model);
		}
	}
}