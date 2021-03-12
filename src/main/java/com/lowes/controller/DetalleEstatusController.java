package com.lowes.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class DetalleEstatusController {

	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	
	@Autowired
	private ParametroService parametroService;

	public DetalleEstatusController() {
		logger.info("DetalleEstatusController()");
	}

	@RequestMapping("/detalleEstatus")
	public ModelAndView detalleEstatus(@ModelAttribute SolicitudAutorizacion solicitudAutorizacion) {
		List<SolicitudAutorizacion> solicitudAutorizacionList = solicitudAutorizacionService
				.getAllSolicitudAutorizacion();
		return new ModelAndView("detalleEstatus", "statusList", solicitudAutorizacionList);
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getEstatus", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		List<SolicitudAutorizacion> solicitudAutorizacionList = null;

		String json = null;
		ObjectMapper map = new ObjectMapper();
		HashMap<String, String> result = new HashMap<String, String>();
		StringBuilder tabla = new StringBuilder();
		String fecha;
		
		DateFormat fechaF = new SimpleDateFormat("dd/MM/yyyy / HH:mm");

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			solicitudAutorizacionList = solicitudAutorizacionService
					.getAllSolicitudAutorizacionBySolicitud((Integer.parseInt(intxnId)));
		}

		int i = 1;
		String colorEstado ="";
		// seteando la respuesta
		if (solicitudAutorizacionList != null && solicitudAutorizacionList.size() > Etiquetas.CERO) {
			for (SolicitudAutorizacion var : solicitudAutorizacionList) {
				String motivo = " ";
				if(var.getUltimoMovimiento() != (short) -1){
					
					//mostrar o no mostrar repeticiones de autorizadores.
					if(Integer.valueOf(parametroService.getParametroByName("muestraAutorizadoresDuplicados").getValor()) == 0 && var.getVisible() != null && var.getVisible() == 0){
						continue;
					}else{
					if (var.getEstadoAutorizacion().getIdEstadoAutorizacion() == 1) { colorEstado = "label-primary";}
					if (var.getEstadoAutorizacion().getIdEstadoAutorizacion() == 2) { colorEstado = "label-success";}
					if (var.getEstadoAutorizacion().getIdEstadoAutorizacion() == 3) { colorEstado = "label-danger";}
					if (var.getMotivoRechazo() != null){motivo = var.getMotivoRechazo();}
					
					tabla.append("<tr class=\"odd gradeX\">");
					tabla.append("<td>" + i + "</td>");
					tabla.append("<td>" + var.getUsuarioByIdUsuarioAutoriza().getNombre() + " " + var.getUsuarioByIdUsuarioAutoriza().getApellidoPaterno() + "</td>");
					tabla.append("<td>" + var.getUsuarioByIdUsuarioAutoriza().getPuesto().getDescripcion() + "</td>");
					tabla.append("<td><label class=\" label "+colorEstado+"\"  >" + var.getEstadoAutorizacion().getEstadoAutorizacion() + "</label></td>");
					fecha = (var.getFechaAutoriza()!=null) ? fechaF.format(var.getFechaAutoriza()).toString() : "";
					tabla.append("<td>" + fecha + "</td>");
					tabla.append("<td>" + motivo + "</td>");
					tabla.append("</tr>");
					i++;
					}
			   }
			}
		}

		result.put("lista", tabla.toString());

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