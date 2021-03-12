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
import com.lowes.entity.ViajeDestino;
import com.lowes.service.UsuarioService;
import com.lowes.service.ViajeDestinoService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ViajeDestinoController {

	private static final Logger logger = Logger.getLogger(ViajeDestinoController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private ViajeDestinoService viajeDestinoService;

	@Autowired
	private UsuarioService usuarioService;

	public ViajeDestinoController() {
		logger.info("ViajeDestinoController()");
	}

	@RequestMapping("/viajeDestino")
	public ModelAndView viajeDestino(@ModelAttribute ViajeDestino viajeDestino) {
		List<ViajeDestino> viajeDestinoList = viajeDestinoService.getAllViajeDestino();
		return new ModelAndView("viajeDestino", "viajeDestinoList", viajeDestinoList);
	}

	@RequestMapping("/saveViajeDestino")
	public ModelAndView saveViajeDestino(@ModelAttribute ViajeDestino viajeDestino) {		
		
		Usuario usuario = usuarioService.getUsuarioSesion();					
		
		if (viajeDestino.getIdViajeDestino() == Etiquetas.CERO) { // si el id es mayor a cero entonces se			
			// trata de una edici�n
			logger.info("Guardando viajeDestino: " + viajeDestino);					
			viajeDestino.setCreacionFecha(new Date());
			viajeDestino.setCreacionUsuario(usuario.getIdUsuario());
			viajeDestino.setActivo(Etiquetas.UNO_S);
			
			//BOOLEANO
	    	if(viajeDestino.getEsViajeInternacionalB()){ viajeDestino.setEsViajeInternacional(Etiquetas.UNO_S);}
	    	else { viajeDestino.setEsViajeInternacional(Etiquetas.CERO_S);}			
			viajeDestinoService.createViajeDestino(viajeDestino);
		} else {
			logger.info("Actualizando viajeDestino: " + viajeDestino);
			ViajeDestino viajeDestinoEdicion = viajeDestinoService.getViajeDestino(viajeDestino.getIdViajeDestino());
			viajeDestinoEdicion.setDescripcion(viajeDestino.getDescripcion());
			viajeDestinoEdicion.setModificacionFecha(new Date());
			viajeDestinoEdicion.setModificacionUsuario(usuario.getIdUsuario());
			
			//BOOLEANO
	    	if(viajeDestino.getEsViajeInternacionalB()){ viajeDestinoEdicion.setEsViajeInternacional(Etiquetas.UNO_S);}
	    	else { viajeDestinoEdicion.setEsViajeInternacional(Etiquetas.CERO_S);}			
			viajeDestinoService.updateViajeDestino(viajeDestinoEdicion);
		}
		return new ModelAndView("redirect:viajeDestino");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getViajeDestino", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		ViajeDestino viajeDestino = new ViajeDestino();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			viajeDestino = viajeDestinoService.getViajeDestino(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("esViajeInternacional", String.valueOf(viajeDestino.getEsViajeInternacional()));
		result.put("descripcion", viajeDestino.getDescripcion());
		result.put("idViajeDestino", String.valueOf(viajeDestino.getIdViajeDestino()));

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
	@RequestMapping("/deleteViajeDestino")
	public ModelAndView deleteViajeDestino(@RequestParam Integer idViajeDestino) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			viajeDestinoService.deleteViajeDestino(idViajeDestino);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:viajeDestino");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:viajeDestino");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}
		return new ModelAndView("redirect:viajeDestino");
	}

}