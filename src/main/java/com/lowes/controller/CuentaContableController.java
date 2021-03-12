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

import com.lowes.entity.CuentaContable;
import com.lowes.entity.Usuario;
import com.lowes.service.CuentaContableService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CuentaContableController {

	private static final Logger logger = Logger.getLogger(CuentaContableController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private CuentaContableService cuentaContableService;

	@Autowired
	private UsuarioService usuarioService;

	public CuentaContableController() {
		logger.info("CuentaContableController()");
	}

	@RequestMapping("/cuentaContable")
	public ModelAndView cuentaContable(@ModelAttribute CuentaContable cuentaContable) {
		List<CuentaContable> cuentaContableList = cuentaContableService.getAllCuentaContable();
		return new ModelAndView("cuentaContable", "cuentaContableList", cuentaContableList);
	}

	@RequestMapping("/saveCuentaContable")
	public ModelAndView saveCuentaContable(@ModelAttribute CuentaContable cuentaContable) {
		logger.info("Guardando cuenta Contable: " + cuentaContable);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (cuentaContable.getIdCuentaContable() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edici�n
			cuentaContable.setCreacionFecha(new Date());
			cuentaContable.setCreacionUsuario(usuario.getIdUsuario());
			cuentaContable.setActivo(Etiquetas.UNO_S);
			cuentaContableService.createCuentaContable(cuentaContable);
		} else {
			CuentaContable cuentaContableEdicion = cuentaContableService.getCuentaContable(cuentaContable.getIdCuentaContable());
			
			cuentaContableEdicion.setDescripcion(cuentaContable.getDescripcion());
			cuentaContableEdicion.setNumeroCuentaContable(cuentaContable.getNumeroCuentaContable());
			cuentaContableEdicion.setModificacionFecha(new Date());
			cuentaContableEdicion.setModificacionUsuario(usuario.getIdUsuario());
			cuentaContableService.updateCuentaContable(cuentaContableEdicion);
		}
		return new ModelAndView("redirect:cuentaContable");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getCuentaContable", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		CuentaContable cuentaContable = new CuentaContable();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			cuentaContable = cuentaContableService.getCuentaContable(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", cuentaContable.getDescripcion());
		result.put("numeroCuentaContable", cuentaContable.getNumeroCuentaContable());
		result.put("idCuentaContable", String.valueOf(cuentaContable.getIdCuentaContable()));

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
	@RequestMapping("/deleteCuentaContable")
	public ModelAndView deleteCuentaContable(@RequestParam Integer id) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			cuentaContableService.deleteCuentaContable(id);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:cuentaContable");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:cuentaContable");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());

			}
		}
		return new ModelAndView("redirect:cuentaContable");
	}

}