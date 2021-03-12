package com.lowes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.lowes.dto.AnticipoDTO;
import com.lowes.dto.BeneficiarioDTO;
import com.lowes.dto.ReposicionDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.CompaniaService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ReposicionController {

	
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private LocacionService locacionService;
	@Autowired
	private UsuarioConfSolicitanteService usuarioConfSolicitanteService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SolicitudService solicitudService;
	
	@RequestMapping("/reposicion")
	public ModelAndView anticipo(HttpSession session, Integer id) {
		
		//declaraciones de objetos
		HashMap<String, Object> modelo = new HashMap<>();
		ReposicionDTO reposicionDTO = new ReposicionDTO();
		Solicitud solicitud = new Solicitud();
		Integer idEstadoSolicitud = 0;
 		
		// lista de compa�ias para el combo.
		List<Compania> companiaslst = companiaService.getAllCompania();
		
		//locaciones permitidas por usuario
		// configuraciones permitidas por usuario
		// extraer usuario en session para guardar la solicitud.
		Usuario usuarioSession = usuarioService.getUsuarioSesion();
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO,locacionService.getAllLocaciones(),usuarioSession.getIdUsuario());
		
		// set la locacion correspondiente al usuario
		
		// si trae un parametro numerico para mostrar solicitud se llama al servicio
				if(id != null && id > Etiquetas.CERO){
					solicitud = solicitudService.getSolicitud(id);

				}

		//objetos para la vista
		modelo.put("reposicionDTO", reposicionDTO);
		modelo.put("idEstadoSolicitud", idEstadoSolicitud);

		return new ModelAndView("reposicion",modelo);
		
	}

}
