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

import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.TipoProveedor;
import com.lowes.entity.Usuario;
import com.lowes.service.LocacionService;
import com.lowes.service.ProveedorService;
import com.lowes.service.TipoProveedorService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelrg
 * @version 1.0
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ProveedorController {

	private static final Logger logger = Logger.getLogger(ProveedorController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TipoProveedorService tipoProveedorService;
	
	@Autowired
	private LocacionService locacionService;
	
	public ProveedorController() {
		logger.info("ProveedorController()");
	}
	
	@RequestMapping("/proveedor")
	public ModelAndView listProveedores(@ModelAttribute Proveedor proveedor) {
		List<Proveedor> proveedorList = proveedorService.getAllProveedores();
		
		List<Usuario> usuarioList = usuarioService.getAllUsuarios();
		List<TipoProveedor> tipoProveedorList = tipoProveedorService.getAllTipoProveedor();
		List<Locacion> locacionList = locacionService.getAllLocaciones();
		
//		agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("proveedorList",proveedorList);
		modelo.put("usuarioList", usuarioList);
		modelo.put("tipoProveedorList", tipoProveedorList);
		modelo.put("locacionList", locacionList);
		
		return new ModelAndView("proveedor", "modelo", modelo);
	}

	@RequestMapping("/saveProveedor")
	public ModelAndView saveProveedor(@ModelAttribute Proveedor proveedor) {
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		proveedor.setPermiteAnticipoMultiple(Etiquetas.CERO_S);
		if(Etiquetas.DOS.equals(proveedor.getTipoProveedor().getIdTipoProveedor())){
			if(proveedor.getPermiteAnticipoMultipleB()){
				proveedor.setPermiteAnticipoMultiple(Etiquetas.UNO_S);
			}
		}
		
		// si el id es mayor a cero entonces se trata de una edici�n
		if (proveedor.getIdProveedor() == Etiquetas.CERO) {
			logger.info("Guardando proveedor: " + proveedor);
			proveedor.setProveedorRiesgo(proveedor.getProveedorRiesgo());
			
			proveedor.setActivo(Etiquetas.UNO_S);
			proveedor.setCreacionFecha(new Date());
			proveedor.setCreacionUsuario(usuario.getIdUsuario());
			
			proveedor.setRfc(proveedor.getRfc());
			proveedor.setUsuario(usuarioService.getUsuario(proveedor.getIdUsuario()));
			proveedor.setIdUsuario(proveedor.getIdUsuario());
			
			proveedorService.createProveedor(proveedor);
		} else {
			logger.info("Actualizando proveedor: " + proveedor);
			Proveedor currentProv = proveedorService.getProveedor(proveedor.getIdProveedor());
			
			currentProv.setNumeroProveedor(proveedor.getNumeroProveedor());
			currentProv.setDescripcion(proveedor.getDescripcion());
			currentProv.setContacto(proveedor.getContacto());
			currentProv.setCorreoElectronico(proveedor.getCorreoElectronico());
			currentProv.setProveedorRiesgo(proveedor.getProveedorRiesgo());
			currentProv.setActivo(proveedor.getActivo());
			currentProv.setRfc(proveedor.getRfc());
			currentProv.setUsuario(usuarioService.getUsuario(proveedor.getIdUsuario()));
			currentProv.setIdUsuario(proveedor.getIdUsuario());
			
			if(proveedor.getTipoProveedor()!=null)
				currentProv.setTipoProveedor(proveedor.getTipoProveedor());
			if(proveedor.getLocacion()!=null)
				currentProv.setLocacion(proveedor.getLocacion());
			currentProv.setPermiteAnticipoMultiple(proveedor.getPermiteAnticipoMultiple());
			
			currentProv.setModificacionFecha(new Date());
			currentProv.setModificacionUsuario(usuario.getIdUsuario());
			currentProv.setPermiteAnticipoMultiple(proveedor.getPermiteAnticipoMultiple());
			
			proveedorService.updateProveedor(currentProv);
		}
		return new ModelAndView("redirect:proveedor");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getProveedor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// System.out.println("Send Message UTF-8 ----------------- " +
		// intxnId);

		// traer el usuario para editarlo.
		Proveedor proveedor = new Proveedor();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			proveedor = proveedorService.getProveedor(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		if(proveedor != null){
			// seteando la respuesta
			result.put("idProveedor", String.valueOf(proveedor.getIdProveedor()));
			result.put("numeroProveedor", String.valueOf(proveedor.getNumeroProveedor()));
			result.put("descripcion", proveedor.getDescripcion());
			result.put("contacto", proveedor.getContacto());
			result.put("correoElectronico", proveedor.getCorreoElectronico());
			result.put("proveedorRiesgo", String.valueOf(proveedor.getProveedorRiesgo()));
			result.put("activo", String.valueOf(proveedor.getActivo()));
			result.put("id", String.valueOf(proveedor.getIdProveedor()));
			result.put("rfc", proveedor.getRfc());
			result.put("tipoProveedor", proveedor.getTipoProveedor()!= null ? String.valueOf(proveedor.getTipoProveedor().getIdTipoProveedor()): "-1");
			result.put("locacion", proveedor.getLocacion()!= null ? String.valueOf(proveedor.getLocacion().getIdLocacion()) : "-1");
			result.put("permiteAnticipoMultiple", String.valueOf(proveedor.getPermiteAnticipoMultiple()));
			
			if(proveedor.getUsuario() != null && proveedor.getUsuario().getIdUsuario()>0)
				result.put("idUsuario", String.valueOf(proveedor.getUsuario().getIdUsuario()));
			else 
				result.put("idUsuario", "-1");
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
	@RequestMapping("/deleteProveedor")
	public ModelAndView deleteProveedor(@RequestParam Integer id) throws UnsupportedEncodingException{
		StringBuilder st = new StringBuilder();
		
		try {
			proveedorService.deleteProveedor(id);
		} catch (Exception e) {
			if(e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)){
				logger.error(etiqueta.ERROR_DEPENDENCIAS,e);
				st.append("redirect:proveedor");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}else{
				logger.error(etiqueta.ERROR_DELETE,e);
				st.append("redirect:proveedor");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE,java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}
		return new ModelAndView("redirect:proveedor");
	}
}