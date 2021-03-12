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
import com.lowes.entity.CuentaContable;
import com.lowes.entity.NivelAutoriza;
import com.lowes.entity.Usuario;
import com.lowes.service.AutorizadorCuentaContableService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AutorizadorCuentaContableController {
	
	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizadorCuentaContableService autorizadorCuentaContableService;
	
	@Autowired
	private NivelAutorizaService nivelAutorizaService;
	
	@Autowired
	private CuentaContableService cuentaContableService;
	
	@RequestMapping("/autorizadorCuentaContable")
	public ModelAndView autorizadorCuentaContable(@ModelAttribute AutorizadorCuentaContable autorizadorCuentaContable) {

		// lista de AidConfiguracion
		List<AutorizadorCuentaContable> autorizadorCuentaContableList = autorizadorCuentaContableService.getAllAutorizadorCuentaContable();

		// Enviar combos
		List<CuentaContable> cuentaContableList = cuentaContableService.getAllCuentaContableOrderByNumeroCuentaConable();
		List<NivelAutoriza> nivelAutorizaList = nivelAutorizaService.getAllNivelAutoriza();
		List<Usuario> usuariosList = usuarioService.getAllUsuarios();

		// agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("autorizadorCuentaContableList", autorizadorCuentaContableList);
		modelo.put("cuentaContableList", cuentaContableList);
		modelo.put("nivelAutorizaList", nivelAutorizaList);
		modelo.put("usuariosList", usuariosList);

		// enviar el modelo completo
		return new ModelAndView("autorizadorCuentaContable", "modelo", modelo);
	}
	
	@RequestMapping("/saveAutorizadorCuentaContable")
	public ModelAndView save(@ModelAttribute AutorizadorCuentaContable autorizadorCuentaContable) {

		Usuario usuarioSesion = usuarioService.getUsuarioSesion();

		if (autorizadorCuentaContable.getIdAutorizadorCuentaContable() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edicion
			logger.info("Guardando autorizadorCuentaContable: " + autorizadorCuentaContable);
			autorizadorCuentaContable.setCreacionFecha(new Date());
			autorizadorCuentaContable.setCreacionUsuario(usuarioSesion.getIdUsuario()); // debe tener el valor del id Usuario
			autorizadorCuentaContable.setActivo(Etiquetas.UNO_S);

			autorizadorCuentaContableService.createAutorizadorCuentaContable(autorizadorCuentaContable);
		} else {
			logger.info("Actualizando autorizadorCuentaContable: " + autorizadorCuentaContable);
			
			AutorizadorCuentaContable autorizadorCuentaContableEdicion = autorizadorCuentaContableService.getAutorizadorCuentaContable(autorizadorCuentaContable.getIdAutorizadorCuentaContable());
			
			autorizadorCuentaContableEdicion.setCuentaContable(autorizadorCuentaContable.getCuentaContable());
			autorizadorCuentaContableEdicion.setNivelAutoriza(autorizadorCuentaContable.getNivelAutoriza());
			autorizadorCuentaContableEdicion.setUsuario(autorizadorCuentaContable.getUsuario());
			autorizadorCuentaContableEdicion.setModificacionFecha(new Date());
			autorizadorCuentaContableEdicion.setModificacionUsuario(usuarioSesion.getIdUsuario());

			autorizadorCuentaContableService.updateAutorizadorCuentaContable(autorizadorCuentaContableEdicion);

		}
		return new ModelAndView("redirect:autorizadorCuentaContable");
	}
	
	// metodo ajax para la carga dinamica de edicion.
			@RequestMapping(value = "/getAutorizadorCuentaContableEdit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			public @ResponseBody ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam Integer id,
					HttpServletRequest request, HttpServletResponse response) {

				String json = null;
				HashMap<String, String> result = new HashMap<String, String>();

				AutorizadorCuentaContable autorizadorCuentaContableEdicion = new AutorizadorCuentaContable();

				// traer el usuario para editarlo.
				if (id > 0) {
					autorizadorCuentaContableEdicion = autorizadorCuentaContableService.getAutorizadorCuentaContable(id);
				}

				if (autorizadorCuentaContableEdicion != null) {
					result.put("idAutorizadorCuentaContable", String.valueOf(autorizadorCuentaContableEdicion.getIdAutorizadorCuentaContable()));
					result.put("idCuentaContable", String.valueOf(autorizadorCuentaContableEdicion.getCuentaContable().getIdCuentaContable()));
					result.put("idNivelAutoriza", String.valueOf(autorizadorCuentaContableEdicion.getNivelAutoriza().getIdNivelAutoriza()));
					result.put("idUsuario", String.valueOf(autorizadorCuentaContableEdicion.getUsuario().getIdUsuario()));
				}

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
			@RequestMapping("/deleteAutorizadorCuentaContable")
			public ModelAndView delete(@RequestParam Integer idAutorizadorCuentaContable) throws UnsupportedEncodingException {
				StringBuilder st = new StringBuilder();

				try {
					autorizadorCuentaContableService.deleteAutorizadorCuentaContable(idAutorizadorCuentaContable);
				} catch (Exception e) {
					if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
						logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
						st.append("redirect:autorizadorCuentaContable");
						st.append("?errorHead=");
						st.append(etiqueta.ERROR);
						st.append("&errorBody=");
						st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS, java.nio.charset.StandardCharsets.UTF_8.toString()));
						System.out.println(st.toString());
						return new ModelAndView(st.toString());
					} else {
						logger.error(etiqueta.ERROR_DELETE, e);
						st.append("redirect:autorizadorCuentaContable");
						st.append("?errorHead=");
						st.append(etiqueta.ERROR);
						st.append("&errorBody=");
						st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
						System.out.println(st.toString());
						return new ModelAndView(st.toString());

					}
				}
				return new ModelAndView("redirect:autorizadorCuentaContable");
			}
			
			
			
			@RequestMapping(value = "/existeNivelCuentaContable", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			public @ResponseBody ResponseEntity<String> existeNivel(HttpSession session, 
			@RequestParam Integer idCuenta, @RequestParam Integer idNivel, @RequestParam Integer idRegistro,
					HttpServletRequest request, HttpServletResponse response) {
				
				String json = null;
				HashMap<String, String> result = new HashMap<String, String>();
				
				Boolean existe = false;
				List<AutorizadorCuentaContable> autorizadores = new ArrayList<>();
				autorizadores = autorizadorCuentaContableService.getAutorizadorCuentaContableByCuentaContable(idCuenta);
				
				if(autorizadores != null && autorizadores.isEmpty() == false){
					for(AutorizadorCuentaContable autorizador : autorizadores){
                      if(autorizador.getNivelAutoriza().getIdNivelAutoriza() == idNivel && autorizador.getIdAutorizadorCuentaContable() != idRegistro){
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