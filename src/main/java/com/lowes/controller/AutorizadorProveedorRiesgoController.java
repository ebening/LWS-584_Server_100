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

import com.lowes.entity.AutorizadorLocacion;
import com.lowes.entity.AutorizadorProveedorRiesgo;
import com.lowes.entity.NivelAutoriza;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Usuario;
import com.lowes.service.AutorizadorProveedorRiesgoService;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.ProveedorService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AutorizadorProveedorRiesgoController {
	
	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizadorProveedorRiesgoService autorizadorProveedorRiesgoService;
	
	@Autowired
	private NivelAutorizaService nivelAutorizaService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@RequestMapping("/autorizadorProveedorRiesgo")
	public ModelAndView autorizadorProveedorRiesgo(@ModelAttribute AutorizadorProveedorRiesgo autorizadorProveedorRiesgo) {

		// lista de AidConfiguracion
		List<AutorizadorProveedorRiesgo> autorizadorProveedorRiesgoList = autorizadorProveedorRiesgoService.getAllAutorizadorProveedorRiesgo();

		// Enviar combos
		List<Proveedor> proveedorList = proveedorService.getAllProveedoresByProveedorRiesgo();
		List<NivelAutoriza> nivelAutorizaList = nivelAutorizaService.getAllNivelAutoriza();
		List<Usuario> usuariosList = usuarioService.getAllUsuarios();

		// agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("autorizadorProveedorRiesgoList", autorizadorProveedorRiesgoList);
		modelo.put("proveedorList", proveedorList);
		modelo.put("nivelAutorizaList", nivelAutorizaList);
		modelo.put("usuariosList", usuariosList);

		// enviar el modelo completo
		return new ModelAndView("autorizadorProveedorRiesgo", "modelo", modelo);
	}
	
	@RequestMapping("/saveAutorizadorProveedorRiesgo")
	public ModelAndView save(@ModelAttribute AutorizadorProveedorRiesgo autorizadorProveedorRiesgo) {

		Usuario usuarioSesion = usuarioService.getUsuarioSesion();

		if (autorizadorProveedorRiesgo.getIdAutorizadorProveedorRiesgo() == Etiquetas.CERO) { // si el id es mayor a cero entonces se trata de una edicion
			logger.info("Guardando autorizadorProveedorRiesgo: " + autorizadorProveedorRiesgo);
			autorizadorProveedorRiesgo.setCreacionFecha(new Date());
			autorizadorProveedorRiesgo.setCreacionUsuario(usuarioSesion.getIdUsuario()); // debe tener el valor del id Usuario
			autorizadorProveedorRiesgo.setActivo(Etiquetas.UNO_S);

			autorizadorProveedorRiesgoService.createAutorizadorProveedorRiesgo(autorizadorProveedorRiesgo);
		} else {
			logger.info("Actualizando autorizadorProveedorRiesgo: " + autorizadorProveedorRiesgo);
			
			AutorizadorProveedorRiesgo autorizadorProveedorRiesgoEdicion = autorizadorProveedorRiesgoService.getAutorizadorProveedorRiesgo(autorizadorProveedorRiesgo.getIdAutorizadorProveedorRiesgo());
			
			autorizadorProveedorRiesgoEdicion.setProveedor(autorizadorProveedorRiesgo.getProveedor());
			autorizadorProveedorRiesgoEdicion.setNivelAutoriza(autorizadorProveedorRiesgo.getNivelAutoriza());
			autorizadorProveedorRiesgoEdicion.setUsuario(autorizadorProveedorRiesgo.getUsuario());
			autorizadorProveedorRiesgoEdicion.setModificacionFecha(new Date());
			autorizadorProveedorRiesgoEdicion.setModificacionUsuario(usuarioSesion.getIdUsuario());

			autorizadorProveedorRiesgoService.updateAutorizadorProveedorRiesgo(autorizadorProveedorRiesgoEdicion);

		}
		return new ModelAndView("redirect:autorizadorProveedorRiesgo");
	}
	
	// metodo ajax para la carga dinamica de edicion.
			@RequestMapping(value = "/getAutorizadorProveedorRiesgoEdit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			public @ResponseBody ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam Integer id,
					HttpServletRequest request, HttpServletResponse response) {

				String json = null;
				HashMap<String, String> result = new HashMap<String, String>();

				AutorizadorProveedorRiesgo autorizadorProveedorRiesgoEdicion = new AutorizadorProveedorRiesgo();

				// traer el usuario para editarlo.
				if (id > 0) {
					autorizadorProveedorRiesgoEdicion = autorizadorProveedorRiesgoService.getAutorizadorProveedorRiesgo(id);
				}

				if (autorizadorProveedorRiesgoEdicion != null) {
					result.put("IdAutorizadorProveedorRiesgo", String.valueOf(autorizadorProveedorRiesgoEdicion.getIdAutorizadorProveedorRiesgo()));
					result.put("idProveedor", String.valueOf(autorizadorProveedorRiesgoEdicion.getProveedor().getIdProveedor()));
					result.put("idNivelAutoriza", String.valueOf(autorizadorProveedorRiesgoEdicion.getNivelAutoriza().getIdNivelAutoriza()));
					result.put("idUsuario", String.valueOf(autorizadorProveedorRiesgoEdicion.getUsuario().getIdUsuario()));
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
			@RequestMapping("/deleteAutorizadorProveedorRiesgo")
			public ModelAndView delete(@RequestParam Integer id) throws UnsupportedEncodingException {
				StringBuilder st = new StringBuilder();

				try {
					autorizadorProveedorRiesgoService.deleteAutorizadorProveedorRiesgo(id);
				} catch (Exception e) {
					if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
						logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
						st.append("redirect:autorizadorProveedorRiesgo");
						st.append("?errorHead=");
						st.append(etiqueta.ERROR);
						st.append("&errorBody=");
						st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS, java.nio.charset.StandardCharsets.UTF_8.toString()));
						System.out.println(st.toString());
						return new ModelAndView(st.toString());
					} else {
						logger.error(etiqueta.ERROR_DELETE, e);
						st.append("redirect:autorizadorProveedorRiesgo");
						st.append("?errorHead=");
						st.append(etiqueta.ERROR);
						st.append("&errorBody=");
						st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
						System.out.println(st.toString());
						return new ModelAndView(st.toString());

					}
				}
				return new ModelAndView("redirect:autorizadorProveedorRiesgo");
			}
			
			@RequestMapping(value = "/existeNivelProveedor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			public @ResponseBody ResponseEntity<String> existeNivel(HttpSession session, 
			@RequestParam Integer idProveedor, @RequestParam Integer idNivel, @RequestParam Integer idRegistro,
					HttpServletRequest request, HttpServletResponse response) {
				
				String json = null;
				HashMap<String, String> result = new HashMap<String, String>();
				
				Boolean existe = false;
				List<AutorizadorProveedorRiesgo> autorizadores = new ArrayList<>();
				autorizadores = autorizadorProveedorRiesgoService.getAllAutorizadorProveedorRiesgo();
				
				if (autorizadores != null && autorizadores.isEmpty() == false) {
					for (AutorizadorProveedorRiesgo autorizador : autorizadores) {
						if (autorizador.getProveedor().getIdProveedor() == idProveedor
								&& autorizador.getNivelAutoriza().getIdNivelAutoriza() == idNivel
								&& autorizador.getIdAutorizadorProveedorRiesgo() != idRegistro) {
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