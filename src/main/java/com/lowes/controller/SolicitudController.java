package com.lowes.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Solicitud;
import com.lowes.service.SolicitudService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class SolicitudController {

	private static final Logger logger = Logger.getLogger(SolicitudController.class);

	@Autowired
	private SolicitudService solicitudService;

	// M�todo de prueba del controlador
	@RequestMapping("solicitud")
	public ModelAndView perfil(@ModelAttribute Solicitud solicitud) {

		solicitudService.getSolicitud(solicitud.getIdSolicitud());

		return new ModelAndView("solicitud", "solicitudList", null);
	}

}
