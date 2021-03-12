package com.lowes.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Menu;
import com.lowes.entity.Perfil;
import com.lowes.entity.PerfilMenu;
import com.lowes.entity.Usuario;
import com.lowes.service.MenuService;
import com.lowes.service.PerfilMenuService;
import com.lowes.service.PerfilService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ConfigPerfilController {

	private static final Logger logger = Logger.getLogger(ConfigPerfilController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private PerfilService perfilService;

	@Autowired
	private PerfilMenuService perfilMenuService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UsuarioService usuarioService;
	
	public ConfigPerfilController() {
		logger.info("ConfigPerfilController()");
	}
	
	// M�todo de prueba del controlador
	@RequestMapping("/confperfil")
	public ModelAndView perfil(@ModelAttribute Perfil perfil) {
		List<Perfil> perfilList = perfilService.getAllPerfiles();
		return new ModelAndView("confperfil", "perfilList", perfilList);
	}

	// metodo que inserta y actualiza en la base de datos.
	@RequestMapping(value = "/savePerfilConf", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ModelAndView savePerfil(@ModelAttribute Perfil perfil) {
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (perfil.getIdPerfil() == 0) { // Inserci�n
			perfil.setCreacionUsuario(usuario.getIdUsuario());
			perfil.setCreacionFecha(new Date());
			perfil.setActivo(Etiquetas.UNO_S);
			perfilService.createPerfil(perfil);
		} else {
			Perfil perfilUpdate = perfilService.getPerfil(perfil.getIdPerfil());
			perfilUpdate.setModificacionUsuario(usuario.getIdUsuario());
			perfilUpdate.setModificacionFecha(new Date());
			perfilUpdate.setDescripcion(perfil.getDescripcion());
			perfilUpdate.setComentarios(perfil.getComentarios());
			perfilService.updatePerfil(perfilUpdate);
		}
		return new ModelAndView("redirect:confperfil");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getPerfilConf", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Perfil perfilR = new Perfil();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			perfilR = perfilService.getPerfil(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", perfilR.getDescripcion());
		result.put("comentarios", perfilR.getComentarios());
		result.put("idPerfil", String.valueOf(perfilR.getIdPerfil()));

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
				// System.out.println("Send Message :::::::: : " + json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo para eliminar
	@RequestMapping("/deletePerfilConf")
	public ModelAndView delete(@RequestParam Integer idPerfil) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			perfilService.deletePerfil(idPerfil);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:confperfil");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:confperfil");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}

		}

		return new ModelAndView("redirect:confperfil");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> guardaConfig(HttpSession session, @RequestParam String idPerfil,
			@RequestParam String ids, HttpServletRequest request, HttpServletResponse response) {
		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<Integer,Menu> menus = new HashMap<>(); 

		List<String> idsMenu = Arrays.asList(ids.split("\\s*,\\s*"));

		// eliminar para crear
		perfilMenuService.deletePerfilMenuByPerfil(Integer.parseInt(idPerfil));
		Perfil perfil = perfilService.getPerfil(Integer.parseInt(idPerfil));
		
		//guardar todos los menus dentro de un mapa para consultarlos rapidamente.
		List<Menu> menusBD = menuService.getAllMenu();
		if (menusBD != null && menusBD.isEmpty() == false) {
			for (Menu menu : menusBD) {
				menus.put(menu.getIdMenu(), menu);
			}
		}
		
		
		if(ids != null && ids.isEmpty() == false){
			for (String id : idsMenu) {
				
				id = id.replace("_", "");
				
				Integer idMenu = Integer.parseInt(id);
				PerfilMenu pm = new PerfilMenu();
	
				Menu menu = new Menu();
				menu = menus.get(idMenu);
	
				pm.setMenu(menu);
				pm.setPerfil(perfil);
	
				perfilMenuService.createPerfilMenu(pm);
			}
		}

		result.put("ok", "ok");

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
				// System.out.println("Send Message :::::::: : " + json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getTreeConfPerfil", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getTreeConfPerfil(HttpSession session, @RequestParam String idPerfil,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		List<PerfilMenu> lstPerfilMenu = new ArrayList<>();
		StringBuilder idMenus = new StringBuilder();

		// consulta
		if (Integer.parseInt(idPerfil) > Etiquetas.CERO) {
			lstPerfilMenu = perfilMenuService.getAllPerfilMenus();
		}

		// agrega ids de menu
		if (lstPerfilMenu.isEmpty() == false) {
			for (PerfilMenu lst : lstPerfilMenu) {
				if (lst.getPerfil().getIdPerfil() == Integer.parseInt(idPerfil)) {
					Integer idMenu = lst.getMenu().getIdMenu();
					idMenus.append(idMenu+"_");
					idMenus.append("/");
				}
			}

			if (idMenus.length() > Etiquetas.CERO) {
				idMenus.setLength(idMenus.length() - Etiquetas.UNO);
			}

		}

		// parametros de repsuesta
		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("ids", idMenus.toString());

		// objeto de mapeo de respuesta
		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// response
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/createTree", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public @ResponseBody ResponseEntity<String> createTree(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder html = new StringBuilder();
		// extraer menu de la tabla
		List<Menu> lstMenu = menuService.getAllMenu();

		// prevent null y vacios
		if (lstMenu != null && lstMenu.isEmpty() == false) {

			// extraer todos los nodos padre
			List<Menu> lstNodosPadre = new ArrayList<>();
			for (Menu lst : lstMenu) {
				if (lst.getEsNodo() == Etiquetas.CERO_S) {
					lstNodosPadre.add(lst);
				}
			}

			if (lstNodosPadre.size() > Etiquetas.CERO) {
				for (Menu lstNPadre : lstNodosPadre) {
					html.append("<ul>");
					html.append("<li>");
					html.append("<input class=\"check\" id=\"" + lstNPadre.getIdMenu()+"_"+ "\" type=\"checkbox\"><span>"
							+ lstNPadre.getNombre() + "</span>");
					List<Menu> hijos = getHijos(lstNPadre.getIdMenu(), lstMenu);
					if (hijos != null && hijos.isEmpty() == false) {
						for (Menu h : hijos) {
							html.append("<ul>");
							html.append("<li>");
							html.append("<input class=\"check\" id=\"" + h.getIdMenu()+"_" + "\" type=\"checkbox\"><span>"
									+ h.getNombre() + "</span>");
							List<Menu> nietos = getHijos(h.getIdMenu(), lstMenu);
							if (nietos != null && nietos.isEmpty() == false) {
								html.append("<ul>");
								for (Menu n : nietos) {
									html.append("<li><input class=\"check\"  id=\"" + n.getIdMenu()+"_"
											+ "\" type=\"checkbox\"><span>" + n.getNombre() + "</span></li>");
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

	private List<Menu> getHijos(Integer id, List<Menu> setMenu) {

		List<Menu> childrenAux = new ArrayList<>();

		for (Menu menu : setMenu) {
			if (menu.getMenuPadre() != null && menu.getMenuPadre().getIdMenu() == id) {
				childrenAux.add(menu);
			}
		}
		return childrenAux;
	}

}
