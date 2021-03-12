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

import com.lowes.entity.Parametro;
import com.lowes.entity.Usuario;
import com.lowes.service.ParametroService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ParametroController {
	
	private static final Logger logger = Logger.getLogger(ParametroController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParametroService parametroService;

	public ParametroController() {
		logger.info("ParametroController()");
	}
	
	@RequestMapping("/parametros")
	public ModelAndView parametros(@ModelAttribute Parametro parametro) {
		List<Parametro> parametroList = parametroService.getAllParametroEditable();
		return new ModelAndView("parametros", "parametroList", parametroList);
	}
	
	@RequestMapping("/saveParametro")
	public ModelAndView saveParametro(@ModelAttribute Parametro parametro) {

		Usuario usuario = usuarioService.getUsuarioSesion();

		if (parametro.getIdParametro() == 0) { // si el id es mayor a cero, se trata de una edición
			logger.info("Guardando Parametro: " + parametro);
			
			String tipoDato = parametro.getTipoDato().toUpperCase();
			parametro.setTipoDato(tipoDato);
			parametro.setCreacionFecha(new Date());
			parametro.setCreacionUsuario(usuario.getIdUsuario());
			parametro.setActivo(Etiquetas.UNO_S);
			parametro.setEditable(Etiquetas.UNO_S);
			parametroService.createParametro(parametro);
		} else {
			logger.info("Actualizando Parametro: " + parametro);			
			Parametro parametroEdicion = parametroService.getParametro(parametro.getIdParametro());
			
			String tipoDato = parametro.getTipoDato().toUpperCase();
			parametroEdicion.setParametro(parametro.getParametro());
			parametroEdicion.setAlias(parametro.getAlias());
			parametroEdicion.setValor(parametro.getValor());
			parametroEdicion.setTipoDato(tipoDato);
			parametroEdicion.setModificacionFecha(new Date());
			parametroEdicion.setModificacionUsuario(usuario.getIdUsuario());
			parametroService.updateParametro(parametroEdicion);
		}
		return new ModelAndView("redirect:parametros");
	}
	
	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getParametro", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Parametro parametro = new Parametro();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			parametro = parametroService.getParametro((Integer.parseInt(intxnId)));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("id", String.valueOf(parametro.getIdParametro()));
		result.put("parametro", parametro.getParametro());
		result.put("alias", parametro.getAlias());
		result.put("valor", parametro.getValor() != null ? parametro.getValor() : "");
		result.put("tipoDato", parametro.getTipoDato());

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
	@RequestMapping("/deleteParametro")
	public ModelAndView deleteParametro(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			parametroService.deleteParametro(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:aid");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:aid");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:parametros");
	}

}