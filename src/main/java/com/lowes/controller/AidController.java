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

import com.lowes.entity.Aid;
import com.lowes.entity.Usuario;
import com.lowes.service.AidService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AidController {

	private static final Logger logger = Logger.getLogger(AidController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private AidService aidService;

	@Autowired
	private UsuarioService usuarioService;

	public AidController() {
		logger.info("AidController()");
	}

	@RequestMapping("/aid")
	public ModelAndView aid(@ModelAttribute Aid aid) {
		List<Aid> aidList = aidService.getAllAid();
		return new ModelAndView("aid", "aidList", aidList);
	}

	@RequestMapping("/saveAid")
	public ModelAndView saveAid(@ModelAttribute Aid aid) {
		logger.info("Guardando aid: " + aid);

		Usuario usuario = usuarioService.getUsuarioSesion();

		if (aid.getIdAid() == 0) { // si el id es mayor a cero entonces se trata
									// de una edici�n
			aid.setCreacionFecha(new Date());
			aid.setCreacionUsuario(usuario.getIdUsuario());
			aid.setActivo(Etiquetas.UNO_S);
			aidService.createAid(aid);
		} else {
			Aid aidEdicion = aidService.getAid(aid.getIdAid());
			aidEdicion.setDescripcion(aid.getNumeroDescripcionAid());
			aidEdicion.setAid(aid.getAid());
			aidEdicion.setModificacionFecha(new Date());
			aidEdicion.setModificacionUsuario(usuario.getIdUsuario());
			aidService.updateAid(aidEdicion);
		}
		return new ModelAndView("redirect:aid");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getAid", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Aid aid = new Aid();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			aid = aidService.getAid((Integer.parseInt(intxnId)));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", aid.getNumeroDescripcionAid());
		result.put("aid", aid.getAid());
		result.put("idAid", String.valueOf(aid.getIdAid()));

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
	@RequestMapping("/deleteAid")
	public ModelAndView deleteAid(@RequestParam Integer idAid) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			aidService.deleteAid(idAid);
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
		return new ModelAndView("redirect:aid");
	}
}