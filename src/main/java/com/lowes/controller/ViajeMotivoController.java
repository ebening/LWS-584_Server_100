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

import com.lowes.entity.Usuario;
import com.lowes.entity.ViajeMotivo;
import com.lowes.service.UsuarioService;
import com.lowes.service.ViajeMotivoService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ViajeMotivoController {

	private static final Logger logger = Logger.getLogger(ViajeMotivoController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private ViajeMotivoService viajeMotivoService;

	@Autowired
	private UsuarioService usuarioService;

	public ViajeMotivoController() {
		logger.info("ViajeMotivoController()");
	}

	@RequestMapping("/viajeMotivo")
	public ModelAndView viajeMotivo(@ModelAttribute ViajeMotivo viajeMotivo) {
		List<ViajeMotivo> viajeMotivoList = viajeMotivoService.getAllViajeMotivo();
		return new ModelAndView("viajeMotivo", "viajeMotivoList", viajeMotivoList);
	}

	@RequestMapping("/saveViajeMotivo")
	public ModelAndView saveViajeMotivo(@ModelAttribute ViajeMotivo viajeMotivo) {
		logger.info("Guardando viajeMotivo: " + viajeMotivo);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (viajeMotivo.getIdViajeMotivo() == Etiquetas.CERO) { // si el id es mayor a cero entonces se
			// trata de una edici�n
			viajeMotivo.setCreacionFecha(new Date());
			viajeMotivo.setCreacionUsuario(usuario.getIdUsuario());
			viajeMotivo.setActivo(Etiquetas.UNO_S);
			viajeMotivoService.createViajeMotivo(viajeMotivo);
		} else {
			ViajeMotivo viajeMotivoEdicion = viajeMotivoService.getViajeMotivo(viajeMotivo.getIdViajeMotivo());
			viajeMotivoEdicion.setDescripcion(viajeMotivo.getDescripcion());
			viajeMotivoEdicion.setModificacionFecha(new Date());
			viajeMotivoEdicion.setModificacionUsuario(usuario.getIdUsuario());
			viajeMotivoService.updateViajeMotivo(viajeMotivoEdicion);
		}
		return new ModelAndView("redirect:viajeMotivo");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getViajeMotivo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		ViajeMotivo viajeMotivo = new ViajeMotivo();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			viajeMotivo = viajeMotivoService.getViajeMotivo(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", viajeMotivo.getDescripcion());
		result.put("idViajeMotivo", String.valueOf(viajeMotivo.getIdViajeMotivo()));

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
	@RequestMapping("/deleteViajeMotivo")
	public ModelAndView deleteViajeMotivo(@RequestParam Integer idViajeMotivo) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			viajeMotivoService.deleteViajeMotivo(idViajeMotivo);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:viajeMotivo");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:viajeMotivo");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}
		return new ModelAndView("redirect:viajeMotivo");
	}

}