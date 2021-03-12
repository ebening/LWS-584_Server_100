/**
 * 
 */
package com.lowes.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.lowes.entity.CuentaContable;
import com.lowes.entity.Locacion;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.CuentaContableService;
import com.lowes.service.LocacionService;
import com.lowes.service.TipoSolicitudService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UsuarioConfSolicitanteController {

	private static final Logger logger = Logger.getLogger(UsuarioConfSolicitanteController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private UsuarioConfSolicitanteService usuarioConfSolicitanteService;

	@Autowired
	private TipoSolicitudService tipoSolicitudService;

	@Autowired
	private LocacionService locacionService;

	@Autowired
	private CuentaContableService cuentaContableService;

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioConfSolicitanteController() {
		logger.info("UsuarioConfSolicitanteController()");
	}

	@RequestMapping("/usuarioConfSolicitante")
	public ModelAndView initUsuarioConfSolicitantees(@ModelAttribute UsuarioConfSolicitante usuarioConfSolicitante) {
		List<UsuarioConfSolicitante> usuarioConfSolicitanteList = usuarioConfSolicitanteService
				.getAllUsuarioConfSolicitante();
		List<TipoSolicitud> tipoSolicitudList = tipoSolicitudService.getAllTipoSolicitud();
		List<Locacion> locacionList = locacionService.getAllLocaciones();
		List<CuentaContable> cuentaContableList = cuentaContableService.getAllCuentaContableOrderByNumeroCuentaConable();
		List<Usuario> usuarioList = usuarioService.getAllUsuarios();

		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("usuarioConfSolicitanteList", usuarioConfSolicitanteList);
		model.put("tipoSolicitudList", tipoSolicitudList);
		model.put("locacionList", locacionList);
		model.put("cuentaContableList", cuentaContableList);
		model.put("usuarioList", usuarioList);

		return new ModelAndView("usuarioConfSolicitante", model);
	}

	@RequestMapping("/saveUsuarioConfSolicitante")
	public ModelAndView saveUsuarioConfSolicitante(@ModelAttribute UsuarioConfSolicitante usuarioConfSolicitante) {
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		// si el id es mayor a cero entonces se trata de una edici�n
		if (usuarioConfSolicitante.getIdUsuarioConfSolicitante() == Etiquetas.CERO) {
			logger.info("Guardando usuarioConfSolicitante: " + usuarioConfSolicitante);
			usuarioConfSolicitante.setCreacionFecha(new Date());
			usuarioConfSolicitante.setCreacionUsuario(usuario.getIdUsuario());
			usuarioConfSolicitante.setActivo(Etiquetas.UNO_S);

			usuarioConfSolicitante.setTipoSolicitud(
					tipoSolicitudService.getTipoSolicitud(usuarioConfSolicitante.getIdTipoSolicitud()));
			usuarioConfSolicitante.setIdTipoSolicitud(usuarioConfSolicitante.getIdTipoSolicitud());
			usuarioConfSolicitante.setLocacion(locacionService.getLocacion(usuarioConfSolicitante.getIdLocacion()));
			usuarioConfSolicitante.setIdLocacion(usuarioConfSolicitante.getIdLocacion());
			usuarioConfSolicitante.setCuentaContable(
					cuentaContableService.getCuentaContable(usuarioConfSolicitante.getIdCuentaContable()));
			usuarioConfSolicitante.setIdCuentaContable(usuarioConfSolicitante.getIdCuentaContable());
			usuarioConfSolicitante.setUsuario(usuarioService.getUsuario(usuarioConfSolicitante.getIdUsuario()));
			usuarioConfSolicitante.setIdUsuario(usuarioConfSolicitante.getIdUsuario());

			usuarioConfSolicitanteService.createUsuarioConfSolicitante(usuarioConfSolicitante);
		} else {
			logger.info("Actualizando usuarioConfSolicitante: " + usuarioConfSolicitante);

			UsuarioConfSolicitante currentUsSol = usuarioConfSolicitanteService
					.getUsuarioConfSolicitante(usuarioConfSolicitante.getIdUsuarioConfSolicitante());
			currentUsSol.setCuentaContable(
					cuentaContableService.getCuentaContable(usuarioConfSolicitante.getIdCuentaContable()));
			currentUsSol.setIdCuentaContable(usuarioConfSolicitante.getIdCuentaContable());
			currentUsSol.setLocacion(locacionService.getLocacion(usuarioConfSolicitante.getIdLocacion()));
			currentUsSol.setIdLocacion(usuarioConfSolicitante.getIdLocacion());
			currentUsSol.setTipoSolicitud(
					tipoSolicitudService.getTipoSolicitud(usuarioConfSolicitante.getIdTipoSolicitud()));
			currentUsSol.setIdTipoSolicitud(usuarioConfSolicitante.getIdTipoSolicitud());
			currentUsSol.setUsuario(usuarioService.getUsuario(usuarioConfSolicitante.getIdUsuario()));
			currentUsSol.setIdUsuario(usuarioConfSolicitante.getIdUsuario());

			currentUsSol.setModificacionFecha(new Date());
			currentUsSol.setModificacionUsuario(usuario.getIdUsuario());
			usuarioConfSolicitanteService.updateUsuarioConfSolicitante(currentUsSol);
		}
		return new ModelAndView("redirect:usuarioConfSolicitante");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getUsuarioConfSolicitante", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// System.out.println("Send Message UTF-8 ----------------- " +
		// intxnId);

		// objeto de respuesta
		UsuarioConfSolicitante usuarioConfSolicitante = new UsuarioConfSolicitante();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			usuarioConfSolicitante = usuarioConfSolicitanteService.getUsuarioConfSolicitante(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		if (usuarioConfSolicitante != null) {
			result.put("idUsuarioSolicitante", String.valueOf(usuarioConfSolicitante.getIdUsuarioConfSolicitante()));
			result.put("idCuentaContable",
					String.valueOf(usuarioConfSolicitante.getCuentaContable().getIdCuentaContable()));
			result.put("idLocacion", String.valueOf(usuarioConfSolicitante.getLocacion().getIdLocacion()));
			result.put("idTipoSolicitud",
					String.valueOf(usuarioConfSolicitante.getTipoSolicitud().getIdTipoSolicitud()));
			result.put("idUsuario", String.valueOf(usuarioConfSolicitante.getUsuario().getIdUsuario()));
		}

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
	@RequestMapping("/deleteUsuarioConfSolicitante")
	public ModelAndView deleteUsuarioConfSolicitante(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			usuarioConfSolicitanteService.deleteUsuarioConfSolicitante(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:usuarioConfSolicitante");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:usuarioConfSolicitante");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:usuarioConfSolicitante");
	}
}
