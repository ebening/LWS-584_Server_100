/**
 * 
 */
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

import com.lowes.entity.CategoriaMenor;
import com.lowes.entity.Usuario;
import com.lowes.service.CategoriaMenorService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CategoriaMenorController {

	private static final Logger logger = Logger.getLogger(CategoriaMenorController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private CategoriaMenorService categoriaMenorService;

	@Autowired
	private UsuarioService usuarioService;
	
	public CategoriaMenorController() {
		logger.info("CategoriaMenorController()");
	}
	
	@RequestMapping("/categoriaMenor")
	public ModelAndView listCategoriaMenores(@ModelAttribute CategoriaMenor categoriaMenor) {
		List<CategoriaMenor> categoriaMenorList = categoriaMenorService.getAllCategoriaMenor();
		return new ModelAndView("categoriaMenor", "categoriaMenorList", categoriaMenorList);
	}

	@RequestMapping("/saveCategoriaMenor")
	public ModelAndView saveCategoriaMenor(@ModelAttribute CategoriaMenor categoriaMenor) {
		logger.info("Guardando categoriaMenor: " + categoriaMenor);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		// si el id es mayor a cero entonces se trata de una edici�n
		if (categoriaMenor.getIdCategoriaMenor() == Etiquetas.CERO) {
			categoriaMenor.setCreacionFecha(new Date());
			categoriaMenor.setCreacionUsuario(usuario.getIdUsuario());
			categoriaMenor.setActivo(Etiquetas.UNO_S);
			categoriaMenorService.createCategoriaMenor(categoriaMenor);
		} else {
			CategoriaMenor currentCatMen = categoriaMenorService
					.getCategoriaMenor(categoriaMenor.getIdCategoriaMenor());
			currentCatMen.setDescripcion(categoriaMenor.getDescripcion());
			currentCatMen.setActivo(categoriaMenor.getActivo());
			currentCatMen.setModificacionFecha(new Date());
			currentCatMen.setModificacionUsuario(usuario.getIdUsuario());
			categoriaMenorService.updateCategoriaMenor(currentCatMen);
		}
		return new ModelAndView("redirect:categoriaMenor");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getCategoriaMenor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		CategoriaMenor categoriaMenor = new CategoriaMenor();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			categoriaMenor = categoriaMenorService.getCategoriaMenor(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", categoriaMenor.getDescripcion());
		result.put("id", String.valueOf(categoriaMenor.getIdCategoriaMenor()));

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo para eliminar
	@RequestMapping("/deleteCategoriaMenor")
	public ModelAndView deleteCategoriaMenor(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			categoriaMenorService.deleteCategoriaMenor(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:categoriaMenor");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:categoriaMenor");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:categoriaMenor");
	}
}