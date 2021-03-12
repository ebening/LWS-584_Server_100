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

import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.service.TipoSolicitudService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class TipoSolicitudController {

	private static final Logger logger = Logger.getLogger(TipoSolicitudController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private TipoSolicitudService tipoSolicitudService;

	@Autowired
	private UsuarioService usuarioService;

	public TipoSolicitudController() {
		logger.info("TipoSolicitudController()");
	}

	@RequestMapping("/tipoSolicitud")
	public ModelAndView tipoSolicitud(@ModelAttribute TipoSolicitud tipoSolicitud) {
		List<TipoSolicitud> tipoSolicitudList = tipoSolicitudService.getAllTipoSolicitud();
		return new ModelAndView("tipoSolicitud", "tipoSolicitudList", tipoSolicitudList);
	}

	@RequestMapping("/saveTipoSolicitud")
	public ModelAndView saveTipoSolicitud(@ModelAttribute TipoSolicitud tipoSolicitud) {
		logger.info("Guardando tipo Solicitud: " + tipoSolicitud);

		Usuario usuario = usuarioService.getUsuarioSesion();

		if (tipoSolicitud.getIdTipoSolicitud() == Etiquetas.CERO) { // si el id es mayor acero entonces setrata de una edici�n
			tipoSolicitud.setCreacionFecha(new Date());
			tipoSolicitud.setCreacionUsuario(usuario.getIdUsuario());
			tipoSolicitud.setActivo(Etiquetas.UNO_S);
			
			tipoSolicitud.setMontoConfirmacionPesos(Utilerias.convertStringToBigDecimal(tipoSolicitud.getStrMontoConfirmacionPesos()));
			tipoSolicitud.setMontoConfirmacionDolares(Utilerias.convertStringToBigDecimal(tipoSolicitud.getStrMontoConfirmacionDolares()));
			
			tipoSolicitudService.createTipoSolicitud(tipoSolicitud);
		} else {
			TipoSolicitud tipoSolicitudEdicion = tipoSolicitudService
					.getTipoSolicitud(tipoSolicitud.getIdTipoSolicitud());
			tipoSolicitudEdicion.setDescripcion(tipoSolicitud.getDescripcion());
			tipoSolicitudEdicion.setModificacionFecha(new Date());
			tipoSolicitudEdicion.setModificacionUsuario(usuario.getIdUsuario());
			
			tipoSolicitudEdicion.setMontoConfirmacionPesos(Utilerias.convertStringToBigDecimal(tipoSolicitud.getStrMontoConfirmacionPesos()));
			tipoSolicitudEdicion.setMontoConfirmacionDolares(Utilerias.convertStringToBigDecimal(tipoSolicitud.getStrMontoConfirmacionDolares()));
			
			tipoSolicitudService.updateTipoSolicitud(tipoSolicitudEdicion);
		}
		return new ModelAndView("redirect:tipoSolicitud");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getTipoSolicitud", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		TipoSolicitud tipoSolicitud = new TipoSolicitud();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			tipoSolicitud = tipoSolicitudService.getTipoSolicitud(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", tipoSolicitud.getDescripcion());
		result.put("montoConfirmacionPesos", tipoSolicitud.getMontoConfirmacionPesos().toString());
		result.put("montoConfirmacionDorales", tipoSolicitud.getMontoConfirmacionDolares().toString());
		result.put("idTipoSolicitud", String.valueOf(tipoSolicitud.getIdTipoSolicitud()));

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
	@RequestMapping("/deleteTipoSolicitud")
	public ModelAndView deleteTipoSolicitud(@RequestParam Integer idTipoSolicitud) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			tipoSolicitudService.deleteTipoSolicitud(idTipoSolicitud);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:tipoSolicitud");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:tipoSolicitud");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:tipoSolicitud");
	}
}