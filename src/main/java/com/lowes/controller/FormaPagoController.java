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

import com.lowes.entity.FormaPago;
import com.lowes.service.FormaPagoService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class FormaPagoController {

	private static final Logger logger = Logger.getLogger(FormaPagoController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private FormaPagoService formaPagoService;

	public FormaPagoController() {
		logger.info("FormaPagoController()");
	}

	@RequestMapping("/formaPago")
	public ModelAndView formaPago(@ModelAttribute FormaPago formaPago) {
		List<FormaPago> formaPagoList = formaPagoService.getAllFormaPago();
		return new ModelAndView("formaPago", "formaPagoList", formaPagoList);
	}

	@RequestMapping("/saveFormaPago")
	public ModelAndView saveFormaPago(@ModelAttribute FormaPago formaPago) {

		if (formaPago.getIdFormaPago() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edici�n
			logger.info("Guardando formaPago: " + formaPago);
			
			formaPagoService.createFormaPago(formaPago);
		} else {
			logger.info("Actualizando formaPago: " + formaPago);

			FormaPago formaPagoEd = formaPagoService.getFormaPago(formaPago.getIdFormaPago());
			formaPagoEd.setDescripcion(formaPago.getDescripcion());

			formaPagoService.updateFormaPago(formaPago);
		}
		return new ModelAndView("redirect:formaPago");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getFormaPago", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		FormaPago formaPago = new FormaPago();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			formaPago = formaPagoService.getFormaPago((Integer.parseInt(intxnId)));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", formaPago.getDescripcion());
		result.put("idFormaPago", String.valueOf(formaPago.getIdFormaPago()));
		result.put("descripcionEbs", formaPago.getDescripcionEbs());

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
	@RequestMapping("/deleteFormaPago")
	public ModelAndView deleteFormaPago(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			formaPagoService.deleteFormaPago(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:formaPago");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:formaPago");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:formaPago");
	}

}