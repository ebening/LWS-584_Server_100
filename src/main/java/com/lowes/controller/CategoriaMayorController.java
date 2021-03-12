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

import com.lowes.entity.CategoriaMayor;
import com.lowes.entity.Usuario;
import com.lowes.service.CategoriaMayorService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CategoriaMayorController {

	private static final Logger logger = Logger.getLogger(CategoriaMayorController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private CategoriaMayorService categoriaMayorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public CategoriaMayorController() {
		logger.info("CategoriaMayorController()");
	}
	
	@RequestMapping("/categoriaMayor")
	public ModelAndView categoriaMayor(@ModelAttribute CategoriaMayor categoriaMayor) {
		List<CategoriaMayor> categoriaMayorList = categoriaMayorService.getAllCategoriaMayor();
		return new ModelAndView("categoriaMayor", "categoriaMayorList", categoriaMayorList);
	}

	@RequestMapping("/saveCategoriaMayor")
	public ModelAndView saveCategoriaMayor(@ModelAttribute CategoriaMayor categoriaMayor) {
		logger.info("Guardando categoriaMayor: " + categoriaMayor);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (categoriaMayor.getIdCategoriaMayor() == Etiquetas.CERO) { // si el id es mayor a
															// cero entonces se
															// trata de una
															// edici�n
			categoriaMayor.setCreacionFecha(new Date());
			categoriaMayor.setCreacionUsuario(usuario.getIdUsuario());
			categoriaMayor.setActivo(Etiquetas.UNO_S);
			categoriaMayorService.createCategoriaMayor(categoriaMayor);
		} else {
			CategoriaMayor categoriaMayorEdicion = categoriaMayorService
					.getCategoriaMayor(categoriaMayor.getIdCategoriaMayor());
			categoriaMayorEdicion.setDescripcion(categoriaMayor.getDescripcion());
			categoriaMayorEdicion.setModificacionFecha(new Date());
			categoriaMayorEdicion.setModificacionUsuario(usuario.getIdUsuario());
			categoriaMayorService.updateCategoriaMayor(categoriaMayorEdicion);
		}
		return new ModelAndView("redirect:categoriaMayor");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getCategoriaMayor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		CategoriaMayor categoriaMayor = new CategoriaMayor();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			categoriaMayor = categoriaMayorService.getCategoriaMayor(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", categoriaMayor.getDescripcion());
		result.put("idCategoriaMayor", String.valueOf(categoriaMayor.getIdCategoriaMayor()));

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
	@RequestMapping("/deleteCategoriaMayor")
	public ModelAndView deleteCategoriaMayor(@RequestParam Integer idCategoriaMayor)
			throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			categoriaMayorService.deleteCategoriaMayor(idCategoriaMayor);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:categoriaMayor");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:categoriaMayor");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:categoriaMayor");
	}
}