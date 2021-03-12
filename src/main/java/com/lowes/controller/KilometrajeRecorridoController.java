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

import com.lowes.entity.KilometrajeRecorrido;
import com.lowes.entity.KilometrajeUbicacion;
import com.lowes.entity.Usuario;
import com.lowes.service.KilometrajeRecorridoService;
import com.lowes.service.KilometrajeUbicacionService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class KilometrajeRecorridoController {
	
	private static final Logger logger = Logger.getLogger(KilometrajeRecorrido.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private KilometrajeRecorridoService kilometrajeRecorridoService;
	
	@Autowired
	private KilometrajeUbicacionService kilometrajeUbicacionService;
	
	public KilometrajeRecorridoController() {
		logger.info("KilometrajeRecorridoController()");
	}
	
	@RequestMapping("/kilometrajeRecorrido")
	public ModelAndView kilometrajeRecorrido(@ModelAttribute KilometrajeRecorrido kilometrajeRecorrido) {

		// lista de AidConfiguracion
		List<KilometrajeRecorrido> kilometrajeRecorridoList = kilometrajeRecorridoService.getAllKilometrajeRecorrido();

		// Enviar combos
		List<KilometrajeUbicacion> origenList = kilometrajeUbicacionService.getAllKilometrajeUbicacion();
		List<KilometrajeUbicacion> destinoList = kilometrajeUbicacionService.getAllKilometrajeUbicacion();

		// agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("kilometrajeRecorridoList", kilometrajeRecorridoList);
		modelo.put("origenList", origenList);
		modelo.put("destinoList", destinoList);

		// enviar el modelo completo
		return new ModelAndView("kilometrajeRecorrido", modelo);
	}
	
	@RequestMapping("/saveKilometrajeRecorrido")
	public ModelAndView saveKilometrajeRecorrido(@ModelAttribute KilometrajeRecorrido kilometrajeRecorrido) {

		Usuario usuario = usuarioService.getUsuarioSesion();

		if (kilometrajeRecorrido.getIdKilometrajeRecorrido() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edici�n
			logger.info("Guardando kilometrajeRecorrido: " + kilometrajeRecorrido);
			kilometrajeRecorrido.setCreacionFecha(new Date());
			kilometrajeRecorrido.setCreacionUsuario(usuario.getIdUsuario());
			kilometrajeRecorrido.setActivo(Etiquetas.UNO_S);
			kilometrajeRecorridoService.createKilometrajeRecorrido(kilometrajeRecorrido);
		} else {
			logger.info("Actualizando kilometrajeRecorrido: " + kilometrajeRecorrido);
			
			KilometrajeRecorrido kilometrajeRecorridoEdicion = kilometrajeRecorridoService.getKilometrajeRecorrido(kilometrajeRecorrido.getIdKilometrajeRecorrido());
			
			kilometrajeRecorridoEdicion.setNumeroKilometros(kilometrajeRecorrido.getNumeroKilometros());
			kilometrajeRecorridoEdicion.setKilometrajeUbicacionByIdOrigen(kilometrajeRecorrido.getKilometrajeUbicacionByIdOrigen());
			kilometrajeRecorridoEdicion.setKilometrajeUbicacionByIdDestino(kilometrajeRecorrido.getKilometrajeUbicacionByIdDestino());
			kilometrajeRecorridoEdicion.setModificacionFecha(new Date());
			kilometrajeRecorridoEdicion.setModificacionUsuario(usuario.getIdUsuario()); 
			kilometrajeRecorridoService.updateKilometrajeRecorrido(kilometrajeRecorridoEdicion);

		}
		return new ModelAndView("redirect:kilometrajeRecorrido");
	}
	
	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getKilometrajeRecorrido", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		KilometrajeRecorrido kilometrajeRecorridoEdicion = new KilometrajeRecorrido();

		// traer el usuario para editarlo.
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			kilometrajeRecorridoEdicion = kilometrajeRecorridoService.getKilometrajeRecorrido(Integer.parseInt(intxnId));
		}

		if (kilometrajeRecorridoEdicion != null) {
			result.put("id", String.valueOf(kilometrajeRecorridoEdicion.getIdKilometrajeRecorrido()));
			result.put("numeroKilometros", String.valueOf(kilometrajeRecorridoEdicion.getNumeroKilometros()));
			result.put("idOrigen",
				String.valueOf(kilometrajeRecorridoEdicion.getKilometrajeUbicacionByIdOrigen().getIdUbicacion()));
			result.put("idDestino",
				String.valueOf(kilometrajeRecorridoEdicion.getKilometrajeUbicacionByIdDestino().getIdUbicacion()));
		}

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
//				 System.out.println("Send Message :::::::: : " + json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	// metodo para eliminar
	@RequestMapping("/deleteKilometrajeRecorrido")
	public ModelAndView deleteKilometrajeRecorrido(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			kilometrajeRecorridoService.deleteKilometrajeRecorrido(id);
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
		return new ModelAndView("redirect:kilometrajeRecorrido");
	}

}