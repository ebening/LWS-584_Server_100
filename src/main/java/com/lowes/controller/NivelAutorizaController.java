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

import com.lowes.entity.NivelAutoriza;
import com.lowes.entity.Usuario;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class NivelAutorizaController {

	private static final Logger logger = Logger.getLogger(NivelAutorizaController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private NivelAutorizaService nivelAutorizaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public NivelAutorizaController() {
		logger.info("NivelAutorizaController()");
	}
	
	@RequestMapping("/nivelAutoriza")
	public ModelAndView initNivelAutorizaes(@ModelAttribute NivelAutoriza nivelAutoriza) {
		List<NivelAutoriza> nivelAutorizaList = nivelAutorizaService.getAllNivelAutoriza();
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("nivelAutorizaList", nivelAutorizaList);
		return new ModelAndView("nivelAutoriza", model);
	}

	@RequestMapping("/saveNivelAutoriza")
	public ModelAndView saveNivelAutoriza(@ModelAttribute NivelAutoriza nivelAutoriza) {
		logger.info("Guardando nivelAutoriza: " + nivelAutoriza);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		// si el id es mayor a cero entonces se trata de una edici�n
		if (nivelAutoriza.getIdNivelAutoriza() == Etiquetas.CERO) {
			nivelAutoriza.setCreacionFecha(new Date());
			nivelAutoriza.setCreacionUsuario(usuario.getIdUsuario());
			nivelAutoriza.setActivo(Etiquetas.UNO_S);
			nivelAutorizaService.createNivelAutoriza(nivelAutoriza);
		} else {
			NivelAutoriza currNivAut = nivelAutorizaService.getNivelAutoriza(nivelAutoriza.getIdNivelAutoriza());
			currNivAut.setDolaresLimite(nivelAutoriza.getDolaresLimite());
			currNivAut.setPesosLimite(nivelAutoriza.getPesosLimite());
			currNivAut.setInferiorSuperior(nivelAutoriza.getInferiorSuperior());
			currNivAut.setActivo(Etiquetas.UNO_S);
			currNivAut.setModificacionFecha(new Date());
			currNivAut.setModificacionUsuario(usuario.getIdUsuario());
			nivelAutorizaService.updateNivelAutoriza(currNivAut);
		}
		return new ModelAndView("redirect:nivelAutoriza");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getNivelAutoriza", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// System.out.println("Send Message UTF-8 ----------------- " +
		// intxnId);

		// objeto de respuesta
		NivelAutoriza nivelAutoriza = new NivelAutoriza();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			nivelAutoriza = nivelAutorizaService.getNivelAutoriza(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("dolaresLimite", String.valueOf(nivelAutoriza.getDolaresLimite()));
		result.put("pesosLimite", String.valueOf(nivelAutoriza.getPesosLimite()));
		result.put("inferiorSuperior", String.valueOf(nivelAutoriza.getInferiorSuperior()));
		result.put("id", String.valueOf(nivelAutoriza.getIdNivelAutoriza()));

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
	@RequestMapping("/deleteNivelAutoriza")
	public ModelAndView deleteNivelAutoriza(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			nivelAutorizaService.deleteNivelAutoriza(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:nivelAutoriza");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:nivelAutoriza");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:nivelAutoriza");
	}
}