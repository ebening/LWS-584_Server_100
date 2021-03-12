package com.lowes.controller;

import java.util.HashMap;
import java.util.Map;

import mx.adinfi.mail.sender.EmailSender;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Parametro;
import com.lowes.entity.Usuario;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioService;
import com.lowes.service.VwSolicitudResumenAutorizacionService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class DashboardController {

	private static final Logger logger = Logger.getLogger(DashboardController.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private VwSolicitudResumenAutorizacionService vwSolicitudResumenAutorizacionService;

	@Autowired
	private ParametroService parametroService;

	@Autowired
	private EmailSender emailSender;

	public DashboardController() {
		logger.info("DashboardController()");
	}

	// // Mï¿½todo de prueba del controlador
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		logger.info("Cargando dashboard");

		// Carga del usuario en sesion
		Usuario usuario = usuarioService.getUsuarioSesion();

		logger.info("Usuario: " + usuario.getCuenta());

		Parametro puestoAP = parametroService.getParametroByName("puestoAutorizacionAP");
		Parametro puestoAP2 = parametroService.getParametroByName("puestoValidacionAP");
		Parametro puestoConfirmacionAP = parametroService.getParametroByName("puestoConfirmacionAP");

		Map<String, Integer> dashboardValues = new HashMap<>();
		
		/* CONTEO DE SOLICITUDES NUEVAS */
		Integer solicitudesNuevas = 0;
		solicitudesNuevas = solicitudesNuevas + solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),Etiquetas.ID_ESTADO_SOLICITUD_NUEVA);
		/* EXEPCION DE CAJA CHICA POR USUARIO CREACION. */
		solicitudesNuevas = solicitudesNuevas + solicitudService.getSolicitudCountByUsuarioEstatusCajaChica(usuario.getIdUsuario(),Etiquetas.ID_ESTADO_SOLICITUD_NUEVA);
		
		dashboardValues.put("capturadas",solicitudesNuevas);

		/* SOLICITUDES ENVIADAS A AUTORIZACIÓN Y AUTORIZADAS */
		Integer solicitudesEnAutorizacionYAutorizadas = 0;
        solicitudesEnAutorizacionYAutorizadas = solicitudesEnAutorizacionYAutorizadas + solicitudService.getSolicitudCountByUsuarioEstatusDoble(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR, Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA);
		/* EXEPCION DE CAJA CHICA POR USUARIO CREACION. */
        solicitudesEnAutorizacionYAutorizadas = solicitudesEnAutorizacionYAutorizadas + solicitudService.getSolicitudCountByUsuarioEstatusDobleCajaChica(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR, Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA);
		dashboardValues.put("autorizacion", solicitudesEnAutorizacionYAutorizadas);
		
		/* ?? OBSOLETO POR MODIFICACION DE DASHBOARD: SE UNIO AUTORIZADAS Y RECHAZADAS.*/
		dashboardValues.put("autorizadas", solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA));
		
		/* SOLICITUDES RECHAZADAS */
        Integer solicitudesRechazadas = 0;
        solicitudesRechazadas = solicitudesRechazadas +	solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA);
		/* EXEPCION DE CAJA CHICA POR USUARIO CREACION. */
        solicitudesRechazadas = solicitudesRechazadas + solicitudService.getSolicitudCountByUsuarioEstatusCajaChica(usuario.getIdUsuario(),Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA);
		dashboardValues.put("rechazadas",solicitudesRechazadas);
		
		/* COMPROBACIONES */
		/* COMPROBACION ANTICIPO */
		Integer comprobacionAnticipo = 0;
		comprobacionAnticipo = comprobacionAnticipo +	solicitudService.getSolicitudCountByAnticipoPentiente(usuario.getIdUsuario(), Etiquetas.SOLICITUD_ANTICIPO,  Etiquetas.ID_ESTADO_SOLICITUD_PAGADA);
		dashboardValues.put("comprobacionAnticipo",comprobacionAnticipo);
		
		/* COMPROBACION ANTICIPO VIAJE */
		Integer comprobacionAnticipoViaje = 0;
		comprobacionAnticipoViaje = comprobacionAnticipoViaje +	solicitudService.getSolicitudCountByAnticipoPentiente(usuario.getIdUsuario(), Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE,  Etiquetas.ID_ESTADO_SOLICITUD_PAGADA);
		dashboardValues.put("comprobacionAnticipoViaje",comprobacionAnticipoViaje);
		
		/* COMPROBACION AMEX */
		Integer comprobacionAmex = 0;
//		comprobacionAmex = comprobacionAmex +	solicitudService.getSolicitudCountByComprobacionAmex(usuario.getIdUsuario(),
//				Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA);
		dashboardValues.put("comprobacionAmex",comprobacionAmex);
		
		/* FIN COMPROBACIONES */
		
		dashboardValues.put("validadas", solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA));
		
		dashboardValues.put("autorizar", vwSolicitudResumenAutorizacionService.getCountSolicitudAutorizacionByUsuario(
				usuario.getIdUsuario(), Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR));
		
		dashboardValues.put("validar", vwSolicitudResumenAutorizacionService.getCountSolicitudAutorizacionByUsuario(
				usuario.getIdUsuario(), Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA));
		
		dashboardValues.put("confirmar", vwSolicitudResumenAutorizacionService.getCountSolicitudAutorizacionByUsuario(
				usuario.getIdUsuario(), Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA));
		
		dashboardValues.put("pendientesConfirmar", vwSolicitudResumenAutorizacionService.getCountSolicitudAutorizacionByUsuario(
				usuario.getIdUsuario(), Etiquetas.ID_ESTADO_SOLICITUD_POR_CONFIRMAR));
		
		dashboardValues.put("esSolicitante",
				usuario.getEsSolicitante() == Etiquetas.UNO ? Etiquetas.UNO : Etiquetas.CERO);
		
		dashboardValues.put("esAutorizador",usuario.getEsAutorizador() == Etiquetas.UNO ? Etiquetas.UNO : Etiquetas.CERO);
		dashboardValues.put("esAP", usuario.getPuesto().getIdPuesto() == Integer.parseInt(puestoAP.getValor())? Etiquetas.UNO : Etiquetas.CERO);
		
		dashboardValues.put("esAP2", usuario.getPuesto().getIdPuesto() == Integer.parseInt(puestoAP2.getValor())
				? Etiquetas.UNO : Etiquetas.CERO);
		
		dashboardValues.put("puestoConfirmacionAP", usuario.getPuesto().getIdPuesto() == Integer.parseInt(puestoConfirmacionAP.getValor())
				? Etiquetas.UNO : Etiquetas.CERO);
		
		return new ModelAndView("dashboard", dashboardValues);

	}

	@RequestMapping(value = "/testemail", method = RequestMethod.GET)
	public ModelAndView sender() {

		try {
			emailSender.sendMail("josues@adinfi.com",
					"<html xmlns=\"http://www.w3.org/1999/xhtml\"> <body style=\"margin: 0; padding: 0; background-color: #F2F2F2;\"> HOLA MUNDO </body> </html>",
					"Subject configurable");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Cargando dashboard");

		// Carga del usuario en sesion
		Usuario usuario = usuarioService.getUsuarioSesion();

		logger.info("Usuario: " + usuario.getCuenta());

		Parametro puestoAP = parametroService.getParametroByName("puestoAutorizacionAP");

		Map<String, Integer> dashboardValues = new HashMap<>();
		dashboardValues.put("capturadas", solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_NUEVA));
		dashboardValues.put("autorizacion", solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR));
		dashboardValues.put("rechazadas", solicitudService.getSolicitudCountByUsuarioEstatus(usuario.getIdUsuario(),
				Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA));
		dashboardValues.put("autorizar", vwSolicitudResumenAutorizacionService.getCountSolicitudAutorizacionByUsuario(
				usuario.getIdUsuario(), Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR));
		dashboardValues.put("validar", vwSolicitudResumenAutorizacionService.getCountSolicitudAutorizacionByUsuario(
				usuario.getIdUsuario(), Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA));
		dashboardValues.put("esSolicitante",
				usuario.getEsSolicitante() == Etiquetas.UNO ? Etiquetas.UNO : Etiquetas.CERO);
		dashboardValues.put("esAutorizador",
				usuario.getEsAutorizador() == Etiquetas.UNO ? Etiquetas.UNO : Etiquetas.CERO);
		dashboardValues.put("esAP", usuario.getPuesto().getIdPuesto() == Integer.parseInt(puestoAP.getValor())
				? Etiquetas.UNO : Etiquetas.CERO);

		return new ModelAndView("dashboard", dashboardValues);

	}
}
