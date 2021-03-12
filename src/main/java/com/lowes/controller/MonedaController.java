package com.lowes.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import com.lowes.entity.Moneda;
import com.lowes.entity.Parametro;
import com.lowes.entity.Usuario;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class MonedaController {

	private static final Logger logger = Logger.getLogger(MonedaController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private MonedaService monedaService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParametroService parametroService;

	public MonedaController() {
		logger.info("MonedaController()");
	}

	@RequestMapping("/moneda")
	public ModelAndView moneda(@ModelAttribute Moneda moneda) {
		List<Moneda> monedaList = monedaService.getAllMonedas();
		List<Parametro> parametros = parametroService.getParametrosByName("tipoMoneda");
		List<Moneda> tipoMonedaList = new ArrayList<Moneda>();
		for (Parametro parametro : parametros) {
			Moneda m = new Moneda();
			m.setDescripcion(parametro.getValor().trim());
			tipoMonedaList.add(m);
		}
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("tipoMonedaList", tipoMonedaList);
		model.put("monedaList", monedaList);
		
		return new ModelAndView("moneda", model);
	}

	@RequestMapping("/saveMoneda")
	public ModelAndView saveMoneda(@ModelAttribute Moneda moneda) {
		logger.info("Guardando moneda: " + moneda);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (moneda.getIdMoneda() == Etiquetas.CERO) { // si el id es mayor a cero entonces se
											// trata de una edici�n
			moneda.setCreacionFecha(new Date());
			moneda.setCreacionUsuario(usuario.getIdUsuario());
			moneda.setActivo(Etiquetas.UNO_S);
			moneda.setDescripcionCortaVariantes("DEFAULT");
			moneda.setDescripcion(""+moneda.getDescripcion().trim());
			monedaService.createMoneda(moneda);
		} else {
			Moneda monedaEdicion = monedaService.getMoneda(moneda.getIdMoneda());
			monedaEdicion.setDescripcion(moneda.getDescripcion());
			monedaEdicion.setDescripcionCorta(moneda.getDescripcionCorta());
			monedaEdicion.setModificacionFecha(new Date());
			monedaEdicion.setModificacionUsuario(usuario.getIdUsuario());
			monedaService.updateMoneda(monedaEdicion);
		}
		return new ModelAndView("redirect:moneda");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getMoneda", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Moneda moneda = new Moneda();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			moneda = monedaService.getMoneda(Integer.parseInt(intxnId));
		}

		String json = null;
//		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, Object> result = new HashMap<String, Object>();

		// seteando la respuesta
		//String tipoMonedaPesos = parametroService.getParametrosByName("tipoMoneda");
		List<Parametro> parametros = parametroService.getParametrosByName("tipoMoneda");
		List<Moneda> tipoMonedaList = new ArrayList<Moneda>();
		for (Parametro parametro : parametros) {
			Moneda m = new Moneda();
			if (moneda.getDescripcion().trim().equals(parametro.getValor().trim())){
				m.setIdMoneda(moneda.getIdMoneda());
				m.setDescripcion(parametro.getValor());
			}
			tipoMonedaList.add(m);
		}
		result.put("tipoMonedaList", tipoMonedaList);
		result.put("descripcion", moneda.getDescripcion());
		result.put("descripcionCorta", moneda.getDescripcionCorta());
		result.put("idMoneda", String.valueOf(moneda.getIdMoneda()));

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
	@RequestMapping("/deleteMoneda")
	public ModelAndView deleteMoneda(@RequestParam Integer idMoneda) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			monedaService.deleteMoneda(idMoneda);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:moneda");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:moneda");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}
		return new ModelAndView("redirect:moneda");
	}

}