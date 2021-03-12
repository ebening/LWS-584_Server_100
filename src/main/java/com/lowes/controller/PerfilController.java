package com.lowes.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
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

import com.lowes.entity.Perfil;
import com.lowes.entity.Usuario;
import com.lowes.service.PerfilService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class PerfilController {

	private static final Logger logger = Logger.getLogger(PerfilController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UsuarioService usuarioService;

	public PerfilController() {
		logger.info("PerfilController()");
	}

	// M�todo de prueba del controlador
	@RequestMapping("/perfil")
	public ModelAndView perfil(@ModelAttribute Perfil perfil) {
		List<Perfil> perfilList = perfilService.getAllPerfiles();
		return new ModelAndView("perfil", "perfilList", perfilList);
	}

	// metodo que inserta y actualiza en la base de datos.
	@RequestMapping("/savePerfil")
	public ModelAndView savePerfil(@ModelAttribute Perfil perfil) {
		logger.info("Guardando Perfil: " + perfil);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (perfil.getIdPerfil() == Etiquetas.CERO) { // Inserci�n
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
		return new ModelAndView("redirect:perfil");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getPerfil", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
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
		result.put("comentarios", perfilR.getComentarios());
		result.put("descripcion", perfilR.getDescripcion());
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
	@RequestMapping("/deletePerfil")
	public ModelAndView deleteEmployee(@RequestParam Integer idPerfil) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			perfilService.deletePerfil(idPerfil);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:perfil");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:perfil");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:perfil");
	}
}
