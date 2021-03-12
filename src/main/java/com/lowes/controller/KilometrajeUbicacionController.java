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

import com.lowes.entity.KilometrajeUbicacion;
import com.lowes.entity.Usuario;
import com.lowes.service.KilometrajeUbicacionService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class KilometrajeUbicacionController {
	
	private static final Logger logger = Logger.getLogger(KilometrajeUbicacion.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private KilometrajeUbicacionService kilometrajeUbicacionService;

	public KilometrajeUbicacionController() {
		logger.info("KilometrajeUbicacionController()");
	}
	
	@RequestMapping("/kilometrajeUbicacion")
	public ModelAndView kilometrajeUbicacion(@ModelAttribute KilometrajeUbicacion kilometrajeUbicacion) {
		List<KilometrajeUbicacion> kilometrajeUbicacionList = kilometrajeUbicacionService.getAllKilometrajeUbicacion();
		return new ModelAndView("kilometrajeUbicacion", "kilometrajeUbicacionList", kilometrajeUbicacionList);
	}
	
	@RequestMapping("/saveKilometrajeUbicacion")
	public ModelAndView saveKilometrajeUbicacion(@ModelAttribute KilometrajeUbicacion kilometrajeUbicacion) {

		Usuario usuario = usuarioService.getUsuarioSesion();

		if (kilometrajeUbicacion.getIdUbicacion() == 0) { // si el id es mayor a cero entonces se trata
									// de una edici�n
			logger.info("Guardando KilometrajeUbicacion: " + kilometrajeUbicacion);
			kilometrajeUbicacion.setCreacionFecha(new Date());
			kilometrajeUbicacion.setCreacionUsuario(usuario.getIdUsuario());
			kilometrajeUbicacion.setActivo(Etiquetas.UNO_S);
			kilometrajeUbicacionService.createKilometrajeUbicacion(kilometrajeUbicacion);
		} else {
			logger.info("Actualizando KilometrajeUbicacion: " + kilometrajeUbicacion);
			KilometrajeUbicacion kilometrajeUbicacionEdicion = kilometrajeUbicacionService.getKilometrajeUbicacion(kilometrajeUbicacion.getIdUbicacion());
			kilometrajeUbicacionEdicion.setDescripcion(kilometrajeUbicacion.getDescripcion());
			kilometrajeUbicacionEdicion.setModificacionFecha(new Date());
			kilometrajeUbicacionEdicion.setModificacionUsuario(usuario.getIdUsuario());
			kilometrajeUbicacionService.updateKilometrajeUbicacion(kilometrajeUbicacionEdicion);
		}
		return new ModelAndView("redirect:kilometrajeUbicacion");
	}
	
	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getKilometrajeUbicacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		KilometrajeUbicacion kilometrajeUbicacion = new KilometrajeUbicacion();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			kilometrajeUbicacion = kilometrajeUbicacionService.getKilometrajeUbicacion(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", kilometrajeUbicacion.getDescripcion());
		result.put("id", String.valueOf(kilometrajeUbicacion.getIdUbicacion()));

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
	@RequestMapping("/deleteKilometrajeUbicacion")
	public ModelAndView deleteKilometrajeUbicacion(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			kilometrajeUbicacionService.deleteKilometrajeUbicacion(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:aid");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:aid");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:kilometrajeUbicacion");
	}
	
}