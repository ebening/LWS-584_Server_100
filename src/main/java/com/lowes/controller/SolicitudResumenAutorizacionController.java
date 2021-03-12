package com.lowes.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.BusquedaFechasIdDTO;
import com.lowes.dto.VwSolicitudResumenAutorizacionDTO;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.VwSolicitudResumenAutorizacion;
import com.lowes.service.EmailService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudManagerService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioService;
import com.lowes.service.VwSolicitudResumenAutorizacionService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class SolicitudResumenAutorizacionController {

	private static final Logger logger = Logger.getLogger(DashboardController.class);
	Etiquetas etiquetas = new Etiquetas("es");

	@Autowired
	private VwSolicitudResumenAutorizacionService vwSolicitudResumenAutorizacionService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private	SolicitudManagerService solicitudManagerService;
	
	@Autowired
	private SolicitudService solicitudService;

	// Al cargar la página
	@RequestMapping(value = "solicitudResumenAutorizacion", method = RequestMethod.GET)
	public ModelAndView cargaSolicitudResumenAutorizacion(@RequestParam Integer idEstadoSolicitud) {
		logger.info("Cargando solicitudResumenAutorizacion");

		Usuario usuario = usuarioService.getUsuarioSesion();

		List<VwSolicitudResumenAutorizacion> vwSolicitudResumenAutorizacion = vwSolicitudResumenAutorizacionService
				.getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(usuario.getIdUsuario(),
						idEstadoSolicitud, null, null);
		
		VwSolicitudResumenAutorizacionDTO wSolicitudResumenAutorizacionDTO = new VwSolicitudResumenAutorizacionDTO();
		wSolicitudResumenAutorizacionDTO.setVwSolicitudResumenAutorizaciones(vwSolicitudResumenAutorizacion);
		
		if (wSolicitudResumenAutorizacionDTO != null){
			for (VwSolicitudResumenAutorizacion vsra : wSolicitudResumenAutorizacionDTO.getVwSolicitudResumenAutorizaciones()){
				if (vsra.getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO){
					vsra.setAutorizar(true);
				}
			}
		}
		
		Map<String, Object> sendItems = new HashMap<>();
		sendItems.put("vwSolicitudResumenAutorizacionDTO", wSolicitudResumenAutorizacionDTO);
		sendItems.put("busquedaFechasIdDTO", new BusquedaFechasIdDTO());
		sendItems.put("idEstadoSolicitud", idEstadoSolicitud);
		sendItems.put("mensajeSuccess", null);
		
		return new ModelAndView("solicitudResumenAutorizacion", sendItems);
	}

	// Al filtrar por fecha
	@RequestMapping(value = "iniciarBusquedaAutorizacion", method = RequestMethod.POST)
	public ModelAndView busquedaPorFecha(@ModelAttribute("BusquedaFechasIdDTO") BusquedaFechasIdDTO busquedaFechasIdDTO,
			HttpServletRequest request) {
		logger.info("Cargando iniciarBusqueda");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fechaInicial = null;
		String fechaFinal = null;

		if (busquedaFechasIdDTO.getFechaFinal() != null) {
			fechaInicial = df.format(busquedaFechasIdDTO.getFechaInicial());
		}

		if (busquedaFechasIdDTO.getFechaFinal() != null) {
			fechaFinal = df.format(busquedaFechasIdDTO.getFechaFinal());
		}

		Usuario usuario = usuarioService.getUsuarioSesion();

		List<VwSolicitudResumenAutorizacion> vwSolicitudResumenAutorizacion = vwSolicitudResumenAutorizacionService
				.getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(usuario.getIdUsuario(),
						busquedaFechasIdDTO.getIdBusqueda(), fechaInicial, fechaFinal);
		
		VwSolicitudResumenAutorizacionDTO wSolicitudResumenAutorizacionDTO = new VwSolicitudResumenAutorizacionDTO();
		wSolicitudResumenAutorizacionDTO.setVwSolicitudResumenAutorizaciones(vwSolicitudResumenAutorizacion);
		
		if (wSolicitudResumenAutorizacionDTO != null){
			for (VwSolicitudResumenAutorizacion vsra : wSolicitudResumenAutorizacionDTO.getVwSolicitudResumenAutorizaciones()){
				if (vsra.getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO){
					vsra.setAutorizar(true);
				}
			}
		}
		
		Map<String, Object> sendItems = new HashMap<>();

		sendItems.put("vwSolicitudResumenAutorizacionDTO", wSolicitudResumenAutorizacionDTO);
		sendItems.put("busquedaFechasIdDTO", busquedaFechasIdDTO);
		sendItems.put("idEstadoSolicitud", busquedaFechasIdDTO.getIdBusqueda());
		sendItems.put("mensajeSuccess", null);

		return new ModelAndView("solicitudResumenAutorizacion", sendItems);
	}
	
	@RequestMapping(value = "autorizarSolicitudes", method = RequestMethod.POST)
	public ModelAndView autorizarSolicitudes(@ModelAttribute("VwSolicitudResumenAutorizacionDTO") VwSolicitudResumenAutorizacionDTO vwSolicitudResumenAutorizacionDTO,
			HttpServletRequest request) {
		logger.info("Cargando autorizarSolicitudes");
		
		//Obtención de parámetros previa
		Integer idBusqueda = vwSolicitudResumenAutorizacionDTO.getVwSolicitudResumenAutorizaciones().get(0).getIdEstadoSolicitud();
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		//Variable de mensajes
		StringBuilder mensajeSalida = new StringBuilder();
		
		//Procesamiento de solicitudes con cambio
		for(VwSolicitudResumenAutorizacion vsra : vwSolicitudResumenAutorizacionDTO.getVwSolicitudResumenAutorizaciones()){
			if(vsra.isRechazar()){
				//Envío a solicitud manager para rechazo.
				Solicitud solicitud = solicitudService.getSolicitud(vsra.getIdSolicitud());
				Map<Boolean, String> resultadoLocal = solicitudManagerService.rechazarSolicitud(solicitud, usuario, vsra.getMotivoRechazo());
				if(resultadoLocal.containsKey(true)){
					mensajeSalida.append(solicitud.getIdSolicitud());
					mensajeSalida.append(" : ");
					mensajeSalida.append(resultadoLocal.get(true));
					mensajeSalida.append("<br>");
					Integer envioCorreo = Integer.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());
					if (envioCorreo == Etiquetas.UNO) {
						try {
							emailService.enviarCorreoRechazo(usuarioService.getUsuarioSesion(), solicitud, vsra.getMotivoRechazo());
						} catch (Exception e) {
							logger.info("El mensaje no pudo ser enviado");
						}
					}
				} else {
					mensajeSalida.append(solicitud.getIdSolicitud());
					mensajeSalida.append(" : ");
					mensajeSalida.append(resultadoLocal.get(false));
					mensajeSalida.append("<br>");
				}
			} else if (vsra.isAutorizar()){
				//Envío a solicitud manager para autorización
				Solicitud solicitud = solicitudService.getSolicitud(vsra.getIdSolicitud());
				Map<Boolean, String> resultadoLocal = solicitudManagerService.autorizarSolicitud(solicitud, usuario);
				if(resultadoLocal.containsKey(true)){
					mensajeSalida.append(solicitud.getIdSolicitud());
					mensajeSalida.append(" : ");
					mensajeSalida.append(resultadoLocal.get(true));
					mensajeSalida.append("<br>");
				} else {
					mensajeSalida.append(solicitud.getIdSolicitud());
					mensajeSalida.append(" : ");
					mensajeSalida.append(resultadoLocal.get(false));
					mensajeSalida.append("<br>");
					
				}
			}
		}
		
		//Obtención de resultados después del proceso
		Map<String, Object> sendItems = new HashMap<>();
		
		List<VwSolicitudResumenAutorizacion> vwSolicitudResumenAutorizacion = vwSolicitudResumenAutorizacionService
				.getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(usuario.getIdUsuario(), idBusqueda, null, null);
		
		vwSolicitudResumenAutorizacionDTO.setVwSolicitudResumenAutorizaciones(vwSolicitudResumenAutorizacion);
		
		if (vwSolicitudResumenAutorizacionDTO != null){
			for (VwSolicitudResumenAutorizacion vsra : vwSolicitudResumenAutorizacionDTO.getVwSolicitudResumenAutorizaciones()){
				if (vsra.getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO){
					vsra.setAutorizar(true);
				}
			}
		}
		
		sendItems.put("vwSolicitudResumenAutorizacionDTO", vwSolicitudResumenAutorizacionDTO);
		sendItems.put("busquedaFechasIdDTO", new BusquedaFechasIdDTO());
		sendItems.put("idEstadoSolicitud", idBusqueda);
		sendItems.put("mensajeSuccess", mensajeSalida.toString());

		return new ModelAndView("solicitudResumenAutorizacion", sendItems);
	}

}
