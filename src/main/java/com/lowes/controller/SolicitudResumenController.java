
package com.lowes.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.lowes.entity.ComprobacionAnticipo;
import com.lowes.entity.Solicitud;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.VwSolicitudResumen;
import com.lowes.service.ComprobacionAnticipoService;
import com.lowes.service.TipoSolicitudService;
import com.lowes.service.UsuarioService;
import com.lowes.service.VwSolicitudResumenService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class SolicitudResumenController {

	private static final Logger logger = Logger.getLogger(SolicitudResumenController.class);

	@Autowired
	private VwSolicitudResumenService vwSolicitudResumenService;

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TipoSolicitudService tipoSolicitudService;
	@Autowired
	private ComprobacionAnticipoService comprobacionAnticipoService;
	
	public SolicitudResumenController() {
		logger.info("SolicitudResumenController()");
	}

	// Al cargar página
	//@RequestMapping(value = "solicitudResumen", method = RequestMethod.GET)
	@RequestMapping(value={"solicitudResumen", "solicitudResumenComprobacion"}, method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request, @RequestParam Integer idEstadoSolicitud, @RequestParam(required = false) Integer idEstadoSolicitud2, @RequestParam(required = false) Integer idTipoSolicitud) {
		logger.info("Cargando solicitudResumen");
		
		//get mapping
		String mapping = request.getServletPath();
		//Set Tipo Solicitud
		if(mapping.equals("/solicitudResumen")){
			//Algo especifico para /SolicitudResumen
		}
		else if(mapping.equals("/solicitudResumenComprobacion")){
			//Algo especifico para /solicitudResumenComprobacion
		}
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		List<VwSolicitudResumen> vwSolicitudResumen;
		
		//Para las comprobaciones de anticipo, gasto de viaje y amex
		if(idTipoSolicitud != null){
			// set de solicitudes pendientes de comprobar
			vwSolicitudResumen = vwSolicitudResumenService.getAllVwSolicitudResumenPendientesComprobar(usuario.getIdUsuario(), idEstadoSolicitud, idTipoSolicitud, null, null);
			
			TipoSolicitud comprobacion = null;
			if(idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO)
				comprobacion = tipoSolicitudService.getTipoSolicitud(Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO);
			else if (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE)
				comprobacion = tipoSolicitudService.getTipoSolicitud(Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
			
			// Obtiene la info de las comprobaciones (si existe).
			for(VwSolicitudResumen solicitud : vwSolicitudResumen){				
				solicitud.setIdTipoSolicitudComprobacion(comprobacion.getIdTipoSolicitud());
				solicitud.setTipoSolicitudComprobacion(comprobacion.getDescripcion());
				solicitud.setColorSolicitudComprobacion(comprobacion.getColorSolicitud());
				
				//Obtiene solicitud de comprobacion
				ComprobacionAnticipo compAnticipo = comprobacionAnticipoService.getComprobacionByAnticipo(solicitud.getIdSolicitud());
				if(compAnticipo != null){
					Solicitud solicitudComprobacion = compAnticipo.getSolicitudByIdSolicitudComprobacion();
					if(solicitudComprobacion != null){
						solicitud.setIdSolicitudComprobacion(solicitudComprobacion.getIdSolicitud());
						solicitud.setResultadoViaje(solicitudComprobacion.getConceptoGasto());
					}
				}
			}
		}

		// Si tiene dos parámetros
		else if (idEstadoSolicitud2 != null) {
			
			// set de solicitudes en autorizacion
			vwSolicitudResumen = vwSolicitudResumenService
					.getAllVwSolicitudResumenByUsuarioEstatusSolicitud(usuario.getIdUsuario(), idEstadoSolicitud, null,
							null);
			
			// set de solicitudes autorizadas
			List<VwSolicitudResumen> vwSolicitudResumen2 = vwSolicitudResumenService
					.getAllVwSolicitudResumenByUsuarioEstatusSolicitud(usuario.getIdUsuario(), idEstadoSolicitud2, null,
							null);
			
			
			// Excepcion para solicitudes de caja chica. (set solicitudes de caja chica por creador de solicitud)
			List<VwSolicitudResumen> vwSolicitudResumenCajaChica = new ArrayList<>();
			vwSolicitudResumenCajaChica = vwSolicitudResumenService.getAllVwSolicitudResumenByUsuarioEstatusSolicitudCajaChica(usuario.getIdUsuario(), idEstadoSolicitud, null,
							null);
			
			if(vwSolicitudResumenCajaChica != null && vwSolicitudResumenCajaChica.isEmpty() == false){
				vwSolicitudResumen.addAll(vwSolicitudResumenCajaChica);
			}
			
			
			vwSolicitudResumen.addAll(vwSolicitudResumen2);
			
		}
		// Si solo cuenta con el parámetro de idEstadoSolicitud
		else {
			vwSolicitudResumen = vwSolicitudResumenService
					.getAllVwSolicitudResumenByUsuarioEstatusSolicitud(usuario.getIdUsuario(), idEstadoSolicitud, null,
							null);
			
			
			List<VwSolicitudResumen> vwSolicitudResumenCajaChica = new ArrayList<>();
			vwSolicitudResumenCajaChica = vwSolicitudResumenService.getAllVwSolicitudResumenByUsuarioEstatusSolicitudCajaChica(usuario.getIdUsuario(), idEstadoSolicitud, null,
							null);
			
			if(vwSolicitudResumenCajaChica != null && vwSolicitudResumenCajaChica.isEmpty() == false){
				vwSolicitudResumen.addAll(vwSolicitudResumenCajaChica);
			}
			
		}
		
		//filtrado: eliminar posibles repetidos
		vwSolicitudResumen = vwSolicitudResumen.stream().distinct().collect(Collectors.toList());
		

		Map<String, Object> sendItems = new HashMap<>();

		sendItems.put("vwSolicitudResumen", vwSolicitudResumen);
		sendItems.put("busquedaFechasIdDTO", new BusquedaFechasIdDTO());
		sendItems.put("idEstadoSolicitud", idEstadoSolicitud);
		sendItems.put("idTipoSolicitud", idTipoSolicitud);

		return new ModelAndView(mapping, sendItems);
	}
	
	// Al cargar página
		@RequestMapping(value = "solicitudAnticipo", method = RequestMethod.GET)
		public ModelAndView solicitudAnticipo(@RequestParam Integer idEstadoSolicitud) {
			logger.info("Cargando solicitudAnticipo");

			Usuario usuario = usuarioService.getUsuarioSesion();
			
			List<VwSolicitudResumen> vwSolicitudResumen = vwSolicitudResumenService
						.getAllVwSolicitudResumenByUsuarioEstatusSolicitud(usuario.getIdUsuario(), idEstadoSolicitud, null,
								null);

			Map<String, Object> sendItems = new HashMap<>();

			sendItems.put("vwSolicitudResumen", vwSolicitudResumen);
			sendItems.put("busquedaFechasIdDTO", new BusquedaFechasIdDTO());
			sendItems.put("idEstadoSolicitud", idEstadoSolicitud);

			return new ModelAndView("solicitudAnticipo", sendItems);
		}
	

	// Al filtrar por fecha
	@RequestMapping(value = "iniciarBusqueda", method = RequestMethod.POST)
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

		List<VwSolicitudResumen> vwSolicitudResumen = vwSolicitudResumenService
				.getAllVwSolicitudResumenByUsuarioEstatusSolicitud(usuario.getIdUsuario(),
						busquedaFechasIdDTO.getIdBusqueda(), fechaInicial, fechaFinal);

		Map<String, Object> sendItems = new HashMap<>();

		sendItems.put("vwSolicitudResumen", vwSolicitudResumen);
		sendItems.put("busquedaFechasIdDTO", busquedaFechasIdDTO);
		sendItems.put("idEstadoSolicitud", busquedaFechasIdDTO.getIdBusqueda());

		return new ModelAndView("solicitudResumen", sendItems);
	}
}
