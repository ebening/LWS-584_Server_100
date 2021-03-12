package com.lowes.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import com.lowes.entity.AutorizadorCuentaContable;
import com.lowes.entity.AutorizadorLocacion;
import com.lowes.entity.Locacion;
import com.lowes.entity.NivelAutoriza;
import com.lowes.entity.Usuario;
import com.lowes.service.AutorizadorLocacionService;
import com.lowes.service.LocacionService;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AutorizadorLocacionController {
	
	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizadorLocacionService autorizadorLocacionService;
	
	@Autowired
	private LocacionService locacionService;
	
	@Autowired
	private NivelAutorizaService nivelAutorizaService;
	
	@RequestMapping("/autorizadorLocacion")
	public ModelAndView autorizadorLocacion(@ModelAttribute AutorizadorLocacion autorizadorLocacion) {

		// lista de AidConfiguracion
		List<AutorizadorLocacion> autorizadorLocacionList = autorizadorLocacionService.getAllAutorizadorLocacion();

		// Enviar combos
		List<Locacion> locacionList = locacionService.getAllLocaciones();
		List<NivelAutoriza> nivelAutorizaList = nivelAutorizaService.getAllNivelAutoriza();
		List<Usuario> usuariosList = usuarioService.getAllUsuarios();

		// agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("autorizadorLocacionList", autorizadorLocacionList);
		modelo.put("locacionList", locacionList);
		modelo.put("nivelAutorizaList", nivelAutorizaList);
		modelo.put("usuariosList", usuariosList);

		// enviar el modelo completo
		return new ModelAndView("autorizadorLocacion", "modelo", modelo);
	}
	
	@RequestMapping("/saveAutorizadorLocacion")
	public ModelAndView save(@ModelAttribute AutorizadorLocacion autorizadorLocacion) {

		Usuario usuarioSesion = usuarioService.getUsuarioSesion();

		if (autorizadorLocacion.getIdAutorizadorLocacion() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edicion
			logger.info("Guardando autorizadorLocacion: " + autorizadorLocacion);
			autorizadorLocacion.setCreacionFecha(new Date());
			autorizadorLocacion.setCreacionUsuario(usuarioSesion.getIdUsuario()); // debe tener el valor del id Usuario
			autorizadorLocacion.setActivo(Etiquetas.UNO_S);

			autorizadorLocacionService.createAutorizadorLocacion(autorizadorLocacion);
		} else {
			logger.info("Actualizando autorizadorLocacion: " + autorizadorLocacion);
			
			AutorizadorLocacion autorizadorLocacionEdicion = autorizadorLocacionService.getAutorizadorLocacion(autorizadorLocacion.getIdAutorizadorLocacion());
			
			autorizadorLocacionEdicion.setLocacion(autorizadorLocacion.getLocacion());
			autorizadorLocacionEdicion.setNivelAutoriza(autorizadorLocacion.getNivelAutoriza());
			autorizadorLocacionEdicion.setUsuario(autorizadorLocacion.getUsuario());
			autorizadorLocacionEdicion.setModificacionFecha(new Date());
			autorizadorLocacionEdicion.setModificacionUsuario(usuarioSesion.getIdUsuario());

			autorizadorLocacionService.updateAutorizadorLocacion(autorizadorLocacionEdicion);

		}
		return new ModelAndView("redirect:autorizadorLocacion");
	}
	
	
	// metodo ajax para la carga dinamica de edicion.
		@RequestMapping(value = "/getAutorizadorLocacionEdit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam Integer id,
				HttpServletRequest request, HttpServletResponse response) {

			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();

			AutorizadorLocacion autorizadorLocacionEdicion = new AutorizadorLocacion();

			// traer el usuario para editarlo.
			if (id > 0) {
				autorizadorLocacionEdicion = autorizadorLocacionService.getAutorizadorLocacion(id);
			}

			if (autorizadorLocacionEdicion != null) {
				result.put("idAutorizadorLocacion", String.valueOf(autorizadorLocacionEdicion.getIdAutorizadorLocacion()));
				result.put("idLocacion", String.valueOf(autorizadorLocacionEdicion.getLocacion().getIdLocacion()));
				result.put("idNivelAutoriza", String.valueOf(autorizadorLocacionEdicion.getNivelAutoriza().getIdNivelAutoriza()));
				result.put("idUsuario", String.valueOf(autorizadorLocacionEdicion.getUsuario().getIdUsuario()));
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
		@RequestMapping("/deleteAutorizadorLocacion")
		public ModelAndView delete(@RequestParam Integer idAutorizadorLocacion) throws UnsupportedEncodingException {
			StringBuilder st = new StringBuilder();

			try {
				autorizadorLocacionService.deleteAutorizadorLocacion(idAutorizadorLocacion);
			} catch (Exception e) {
				if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
					logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
					st.append("redirect:autorizadorLocacion");
					st.append("?errorHead=");
					st.append(etiqueta.ERROR);
					st.append("&errorBody=");
					st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS, java.nio.charset.StandardCharsets.UTF_8.toString()));
					System.out.println(st.toString());
					return new ModelAndView(st.toString());
				} else {
					logger.error(etiqueta.ERROR_DELETE, e);
					st.append("redirect:autorizadorLocacion");
					st.append("?errorHead=");
					st.append(etiqueta.ERROR);
					st.append("&errorBody=");
					st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
					System.out.println(st.toString());
					return new ModelAndView(st.toString());

				}
			}
			return new ModelAndView("redirect:autorizadorLocacion");
		}
		
		@RequestMapping(value = "/existeNivelLocacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> existeNivel(HttpSession session, 
		@RequestParam Integer idlocacion, @RequestParam Integer idNivel, @RequestParam Integer idRegistro,
				HttpServletRequest request, HttpServletResponse response) {
			
			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			
			Boolean existe = false;
			List<AutorizadorLocacion> autorizadores = new ArrayList<>();
			autorizadores = autorizadorLocacionService.getAllAutorizadorLocacion();
			
			if (autorizadores != null && autorizadores.isEmpty() == false) {
				for (AutorizadorLocacion autorizador : autorizadores) {
					if (autorizador.getLocacion().getIdLocacion() == idlocacion
							&& autorizador.getNivelAutoriza().getIdNivelAutoriza() == idNivel
							&& autorizador.getIdAutorizadorLocacion() != idRegistro) {
						existe = true;
						break;
					}
				}
			}
			
			//se envia a la vista el resultado, si existe sera true
			result.put("existe",existe.toString());
			
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

}