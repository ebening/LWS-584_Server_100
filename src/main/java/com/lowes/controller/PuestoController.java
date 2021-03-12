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
import com.lowes.entity.Puesto;
import com.lowes.entity.TipoLocacion;
import com.lowes.entity.Usuario;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.PuestoService;
import com.lowes.service.TipoLocacionService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class PuestoController {

	private static final Logger logger = Logger.getLogger(PuestoController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private PuestoService puestoService;

	@Autowired
	private TipoLocacionService tipoLocacionService;

	@Autowired
	private NivelAutorizaService nivelAutorizaService;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/puesto")
	public ModelAndView listPuestoes(@ModelAttribute Puesto puesto) {

		List<Puesto> puestoList = puestoService.getAllPuestos();
		List<TipoLocacion> tipoLocacionList = tipoLocacionService.getAllTipoLocaciones();
		List<NivelAutoriza> nivelAutorizaList = nivelAutorizaService.getAllNivelAutoriza();
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("puestoList", puestoList);
		model.put("tipoLocacionList", tipoLocacionList);
		model.put("nivelAutorizaList", nivelAutorizaList);
		return new ModelAndView("puesto", model);

	}

	@RequestMapping("/savePuesto")
	public ModelAndView savePuesto(@ModelAttribute Puesto puesto) {

		Usuario usuario = usuarioService.getUsuarioSesion();

		// si el id es mayor a cero entonces se trata de una edici�n
		if (puesto.getIdPuesto() == Etiquetas.CERO) {
			logger.info("Guardando puesto: " + puesto);
			puesto.setActivo(Etiquetas.UNO_S);
			puesto.setCreacionFecha(new Date());
			puesto.setCreacionUsuario(usuario.getIdUsuario());

			puesto.setNivelAutoriza(nivelAutorizaService.getNivelAutoriza(puesto.getIdNivelAutoriza()));
			puesto.setIdNivelAutoriza(puesto.getIdNivelAutoriza());
			puesto.setTipoLocacion(tipoLocacionService.getTipoLocacion(puesto.getIdTipoLocacion()));
			puesto.setIdTipoLocacion(puesto.getIdTipoLocacion());
			puestoService.createPuesto(puesto);
		} else {
			logger.info("Actualizando puesto: " + puesto);
			Puesto currPuesto = puestoService.getPuesto(puesto.getIdPuesto());
			currPuesto.setDescripcion(puesto.getDescripcion());
			currPuesto.setActivo(puesto.getActivo());
			currPuesto.setNivelAutoriza(nivelAutorizaService.getNivelAutoriza(puesto.getIdNivelAutoriza()));
			currPuesto.setIdNivelAutoriza(puesto.getIdNivelAutoriza());
			currPuesto.setTipoLocacion(tipoLocacionService.getTipoLocacion(puesto.getIdTipoLocacion()));
			currPuesto.setIdTipoLocacion(puesto.getIdTipoLocacion());
			currPuesto.setModificacionFecha(new Date());
			currPuesto.setModificacionUsuario(usuario.getIdUsuario());
			puestoService.updatePuesto(currPuesto);
		}
		return new ModelAndView("redirect:puesto");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getPuesto", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// System.out.println("Send Message UTF-8 ----------------- " +
		// intxnId);

		// objeto de respuesta
		Puesto puesto = new Puesto();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			puesto = puestoService.getPuesto(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("idPuesto", String.valueOf(puesto.getIdPuesto()));
		result.put("descripcion", puesto.getDescripcion());
		// result.put("activo", String.valueOf(puesto.getActivo()));
		result.put("idTipoLocacion", String.valueOf(puesto.getTipoLocacion().getIdTipoLocacion()));
		if (puesto.getNivelAutoriza() != null) {
			result.put("idNivelAutoriza", String.valueOf(puesto.getNivelAutoriza().getIdNivelAutoriza()));
		} else {
			result.put("idNivelAutoriza", String.valueOf("-1"));
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
	@RequestMapping("/deletePuesto")
	public ModelAndView deletePuesto(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			puestoService.deletePuesto(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:puesto");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:puesto");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:puesto");
	}
}