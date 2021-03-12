package com.lowes.controller;

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

import com.lowes.entity.Factura;
import com.lowes.entity.Solicitud;
import com.lowes.service.FacturaService;
import com.lowes.service.SolicitudService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class DetalleConceptoController {

	private static final Logger logger = Logger.getLogger(DetalleConceptoController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private FacturaService facturaService;

	public DetalleConceptoController() {
		logger.info("DetalleConceptoController()");
	}

	@RequestMapping("/detalleConcepto")
	public ModelAndView detalleConcepto(@ModelAttribute Solicitud solicitud) {
		List<Solicitud> solicitudList = solicitudService.getAllSolicitud();
		return new ModelAndView("detalleConcepto", "solicitudList", solicitudList);
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getSolicitud", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getSolicitud(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Solicitud solicitud = new Solicitud();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			solicitud = solicitudService.getSolicitud((Integer.parseInt(intxnId)));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("conceptoGasto", solicitud.getConceptoGasto());
		result.put("idSolicitud", String.valueOf(solicitud.getIdSolicitud()));

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
	
	@RequestMapping(value = "/getFactura", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getFactura(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Factura factura = new Factura();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			factura = facturaService.getFactura(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("conceptoGasto", factura.getConceptoGasto());
		result.put("idFactura", String.valueOf(factura.getIdFactura()));

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

}