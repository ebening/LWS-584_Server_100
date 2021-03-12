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

import com.lowes.entity.Compania;
import com.lowes.entity.Usuario;
import com.lowes.service.CompaniaService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CompaniaController {

	private static final Logger logger = Logger.getLogger(CompaniaController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private CompaniaService companiaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public CompaniaController() {
		logger.info("CompaniaController()");
	}
	
	@RequestMapping("/compania")
	public ModelAndView compania(@ModelAttribute Compania compania) {
		List<Compania> companiaList = companiaService.getAllCompania();
		return new ModelAndView("compania", "companiaList", companiaList);
	}

	@RequestMapping("/saveCompania")
	public ModelAndView saveCompania(@ModelAttribute Compania compania) {
		logger.info("Guardando compania: " + compania);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (compania.getIdcompania() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de unaedici�n
			compania.setCreacionfecha(new Date());
			compania.setCreacionusuario(usuario.getIdUsuario());
			compania.setActivo(Etiquetas.UNO_S);
			companiaService.createCompania(compania);
		} else {
			compania.setModificacionfecha(new Date()); // new Date()
			compania.setModificacionusuario(usuario.getIdUsuario()); // id del usuario
			companiaService.updateCompania(compania);
		}
		return new ModelAndView("redirect:compania");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getCompania", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Compania compania = new Compania();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			compania = companiaService.getCompania(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", compania.getDescripcion());
		result.put("numeroCompania", compania.getNumeroCompania());
		result.put("rfc", compania.getRfc());
		result.put("idcompania", String.valueOf(compania.getIdcompania()));
		result.put("claveValidacionFiscal", compania.getClaveValidacionFiscal());
		result.put("idOrganizacion", String.valueOf(compania.getIdOrganizacion()));
		result.put("descripcionEbs", compania.getDescripcionEbs());

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo para eliminar
	@RequestMapping("/deleteCompania")
	public ModelAndView deleteCompania(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			companiaService.deleteCompania(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:compania");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:compania");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}

		return new ModelAndView("redirect:compania");
	}

}