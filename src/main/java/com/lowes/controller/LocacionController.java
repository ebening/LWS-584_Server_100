/**
 * 
 */
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

import com.lowes.entity.Locacion;
import com.lowes.entity.TipoLocacion;
import com.lowes.entity.Usuario;
import com.lowes.service.LocacionService;
import com.lowes.service.TipoLocacionService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class LocacionController {

	private static final Logger logger = Logger.getLogger(LocacionController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private LocacionService locacionService;
	
	@Autowired
	private TipoLocacionService tipoLocacionService;
	
	@Autowired
	private UsuarioService usuarioService;

	public LocacionController() {
		logger.info("LocacionController()");
	}

	@RequestMapping("/locacion")
	public ModelAndView initLocaciones(@ModelAttribute Locacion locacion) {
		List<Locacion> locacionList = locacionService.getAllLocaciones();
		List<TipoLocacion> tipoLocacionList = tipoLocacionService.getAllTipoLocaciones();
		
		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		List<Usuario> lstDirectores = new ArrayList<>();
		
		//filtrar directores.
		for(Usuario usr : usuarios){
			String perfil = usr.getPerfil().getDescripcion().toUpperCase();
			if(perfil.contains("DIRECCI")){
			   lstDirectores.add(usr);	
			}
		}
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("locacionList", locacionList);
		model.put("tipoLocacionList", tipoLocacionList);
		model.put("lstDirectores", lstDirectores);

		return new ModelAndView("locacion",model);
	}

	@RequestMapping("/saveLocacion")
	public ModelAndView saveLocacion(@ModelAttribute Locacion locacion) {

		logger.info("Guardando locacion: " + locacion);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		// si el id es mayor a cero entonces se trata de una edici�n
		if (locacion.getIdLocacion() == Etiquetas.CERO) {
			locacion.setCreacionFecha(new Date());
			locacion.setCreacionUsuario(usuario.getIdUsuario());
			locacion.setTipoLocacion(tipoLocacionService.getTipoLocacion(locacion.getIdTipoLocacion()));
			
			if(locacion.getDirector().getIdUsuario() != -1){
			   locacion.setDirector(new Usuario(locacion.getDirector().getIdUsuario()));
			}else{
			   locacion.setDirector(null);
			}
			
			locacion.setActivo(Etiquetas.UNO_S);
			locacionService.createLocacion(locacion);
		} else {
			Locacion currentLoc = locacionService.getLocacion(locacion.getIdLocacion());

			currentLoc.setTipoLocacion(tipoLocacionService.getTipoLocacion(locacion.getIdTipoLocacion()));
			currentLoc.setDescripcion(locacion.getDescripcion());
			currentLoc.setNumero(locacion.getNumero());
			currentLoc.setModificacionFecha(new Date());
			currentLoc.setModificacionUsuario(usuario.getIdUsuario());
			
			if(locacion.getDirector().getIdUsuario() != -1){
				    currentLoc.setDirector(new Usuario(locacion.getDirector().getIdUsuario()));
				}else{
					currentLoc.setDirector(null);
				}
			
			locacionService.updateLocacion(currentLoc);
		}
		return new ModelAndView("redirect:locacion");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getLocacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {
		
		// objeto de respuesta
		Locacion locacionEdicion = new Locacion();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			locacionEdicion = locacionService.getLocacion(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		if (locacionEdicion != null) {
			// seteando la respuesta
			result.put("descripcion", locacionEdicion.getNumeroDescripcionLocacion());
			result.put("numero", String.valueOf(locacionEdicion.getNumero()));
			result.put("id", String.valueOf(locacionEdicion.getIdLocacion()));
			result.put("idTipoLocacion", String.valueOf(locacionEdicion.getTipoLocacion().getIdTipoLocacion()));
			
			if(locacionEdicion.getDirector() != null){
				result.put("director", String.valueOf(locacionEdicion.getDirector().getIdUsuario()));
			}else{
				result.put("director", "-1");
			}
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
	@RequestMapping("/deleteLocacion")
	public ModelAndView deleteLocacion(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			locacionService.deleteLocacion(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:locacion");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:locacion");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:locacion");
	}

}