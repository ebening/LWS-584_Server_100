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
import com.lowes.entity.AidConfiguracion;
import com.lowes.entity.CategoriaMayor;
import com.lowes.entity.CategoriaMenor;
import com.lowes.entity.Compania;
import com.lowes.entity.Usuario;
import com.lowes.service.AidConfiguracionService;
import com.lowes.service.AidService;
import com.lowes.service.CategoriaMayorService;
import com.lowes.service.CategoriaMenorService;
import com.lowes.service.CompaniaService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AidConfiguracionController {

	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private AidConfiguracionService aidConfiguracionService;

	@Autowired
	private AidService aidService;

	@Autowired
	private CategoriaMayorService categoriaMayorService;

	@Autowired
	private CategoriaMenorService categoriaMenorService;

	@Autowired
	private CompaniaService companiaService;

	@Autowired
	private UsuarioService usuarioService;

	public AidConfiguracionController() {
		logger.info("AidConfiguracionController()");
	}

	@RequestMapping("/aidConfiguracion")
	public ModelAndView aidConfiguracion(@ModelAttribute AidConfiguracion aidConfiguracion) {

		// lista de AidConfiguracion
		List<AidConfiguracion> aidConfiguracionList = aidConfiguracionService.getAllAidConfiguracion();

		// Enviar combos
		List<Aid> aidList = aidService.getAllAid();
		List<CategoriaMayor> categoriaMayorList = categoriaMayorService.getAllCategoriaMayor();
		List<CategoriaMenor> categoriaMenorList = categoriaMenorService.getAllCategoriaMenor();
		List<Compania> companiaList = companiaService.getAllCompania();

		// agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("aidConfiguracionList", aidConfiguracionList);
		modelo.put("aidList", aidList);
		modelo.put("companiaList", companiaList);
		modelo.put("categoriaMayorList", categoriaMayorList);
		modelo.put("categoriaMenorList", categoriaMenorList);

		// enviar el modelo completo
		return new ModelAndView("aidConfiguracion", "modelo", modelo);
	}

	@RequestMapping("/saveAidConfiguracion")
	public ModelAndView saveAidConfiguracion(@ModelAttribute AidConfiguracion aidConfiguracion) {
		logger.info("Guardando aidConfiguracion: " + aidConfiguracion);

		Usuario usuario = usuarioService.getUsuarioSesion();

		if (aidConfiguracion.getIdAidConfiguracion() == Etiquetas.CERO) { // si el id es mayor a cero entoncessetratadeunaedici�n
			aidConfiguracion.setCreacionFecha(new Date());
			aidConfiguracion.setCreacionUsuario(usuario.getIdUsuario()); // debe tener el valor del id Usuario
			aidConfiguracion.setActivo(Etiquetas.UNO_S);

			// seteando en duro compa�ia
			aidConfiguracion.setCompania(companiaService.getCompania(aidConfiguracion.getIdCompania()));

			// seteando Aid
			aidConfiguracion.setAid(aidService.getAid(aidConfiguracion.getIdAid()));

			// seteando CategoriaMayor
			aidConfiguracion.setCategoriaMayor(categoriaMayorService.getCategoriaMayor(aidConfiguracion.getIdCategoriaMayor()));

			// seteando CategoriaMenor
			aidConfiguracion.setCategoriaMenor(categoriaMenorService.getCategoriaMenor(aidConfiguracion.getIdCategoriaMenor()));

			aidConfiguracionService.createAidConfiguracion(aidConfiguracion);
		} else {
			AidConfiguracion aidConfiguracionEdicion = aidConfiguracionService.getAidConfiguracion(aidConfiguracion.getIdAidConfiguracion());

			aidConfiguracionEdicion.setAid(aidService.getAid(aidConfiguracion.getIdAid()));
			aidConfiguracionEdicion.setIdAid(aidConfiguracion.getIdAid());
			aidConfiguracionEdicion.setCategoriaMayor(categoriaMayorService.getCategoriaMayor(aidConfiguracion.getIdCategoriaMayor()));
			aidConfiguracionEdicion.setIdCategoriaMayor(aidConfiguracion.getIdCategoriaMayor());
			aidConfiguracionEdicion.setCategoriaMenor(categoriaMenorService.getCategoriaMenor(aidConfiguracion.getIdCategoriaMenor()));
			aidConfiguracionEdicion.setIdCategoriaMenor(aidConfiguracion.getIdCategoriaMenor());
			aidConfiguracionEdicion.setCompania(companiaService.getCompania(aidConfiguracion.getIdCompania()));
			aidConfiguracionEdicion.setIdCompania(aidConfiguracion.getIdCompania());
			aidConfiguracionEdicion.setModificacionFecha(new Date());
			aidConfiguracionEdicion.setModificacionUsuario(usuario.getIdUsuario()); // debe tener el valor de ID Usuario
			aidConfiguracionService.updateAidConfiguracion(aidConfiguracionEdicion);

		}
		return new ModelAndView("redirect:aidConfiguracion");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getAidConfiguracionEdit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		AidConfiguracion aidConfiguracionEdicion = new AidConfiguracion();

		// traer el usuario para editarlo.
		if (id > 0) {
			aidConfiguracionEdicion = aidConfiguracionService.getAidConfiguracion(id);
		}

		if (aidConfiguracionEdicion != null) {
			result.put("idAidConfiguracion", String.valueOf(aidConfiguracionEdicion.getIdAidConfiguracion()));
			result.put("idAid", String.valueOf(aidConfiguracionEdicion.getAid().getIdAid()));
			result.put("idCategoriaMayor", String.valueOf(aidConfiguracionEdicion.getCategoriaMayor().getIdCategoriaMayor()));
			result.put("idCategoriaMenor", String.valueOf(aidConfiguracionEdicion.getCategoriaMenor().getIdCategoriaMenor()));
			result.put("idCompania", String.valueOf(aidConfiguracionEdicion.getCompania().getIdcompania()));
		}

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
				System.out.println("Send Message  :::::::: : " + json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	// metodo para eliminar
	@RequestMapping("/deleteAidConfiguracion")
	public ModelAndView deleteAidConfiguracion(@RequestParam Integer idAidConfiguracion) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			aidConfiguracionService.deleteAidConfiguracion(idAidConfiguracion);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:aidConfiguracion");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:aidConfiguracion");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:aidConfiguracion");
	}

}