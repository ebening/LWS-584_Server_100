package com.lowes.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.lowes.entity.TipoFactura;
import com.lowes.service.TipoFacturaService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class TipoFacturaController {

	private static final Logger logger = Logger.getLogger(TipoFacturaController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private TipoFacturaService tipoFacturaService;
	
	public TipoFacturaController() {
		logger.info("TipoFacturaController()");
	}

	@RequestMapping("/tipoFactura")
	public ModelAndView tipoFactura(@ModelAttribute TipoFactura tipoFactura) {
		List<TipoFactura> tipoFacturaList = tipoFacturaService.getAllTipoFactura();
		return new ModelAndView("tipoFactura", "tipoFacturaList", tipoFacturaList);
	}

	@RequestMapping("/saveTipoFactura")
	public ModelAndView saveTipoFactura(@ModelAttribute TipoFactura tipoFactura) {

		if (tipoFactura.getIdTipoFactura() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edici�n
			logger.info("Guardando TipoFactura: " + tipoFactura);
			
			tipoFacturaService.createTipoFactura(tipoFactura);
		} else {
			logger.info("Actualizando TipoFactura: " + tipoFactura);

			tipoFacturaService.updateTipoFactura(tipoFactura);
		}
		return new ModelAndView("redirect:tipoFactura");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getTipoFactura", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		TipoFactura tipoFactura = new TipoFactura();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			tipoFactura = tipoFacturaService.getTipoFactura(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", tipoFactura.getDescripcion());
		result.put("descripcionEbs", tipoFactura.getDescripcionEbs());
		result.put("idTipoFactura", String.valueOf(tipoFactura.getIdTipoFactura()));

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
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			tipoFacturaService.deleteTipoFactura(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:tipoFactura");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:tipoFactura");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}

		return new ModelAndView("redirect:tipoFactura");
	}

}