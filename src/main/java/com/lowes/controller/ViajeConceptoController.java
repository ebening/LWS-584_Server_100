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
import com.lowes.entity.ViajeConcepto;
import com.lowes.service.UsuarioService;
import com.lowes.service.ViajeConceptoService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ViajeConceptoController {

	private static final Logger logger = Logger.getLogger(ViajeConceptoController.class);
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private ViajeConceptoService viajeConceptoService;

	@Autowired
	private UsuarioService usuarioService;

	public ViajeConceptoController() {
		logger.info("ViajeConceptoController()");
	}

	@RequestMapping("/viajeConcepto")
	public ModelAndView viajeConcepto(@ModelAttribute ViajeConcepto viajeConcepto) {
		List<ViajeConcepto> viajeConceptoList = viajeConceptoService.getAllViajeConcepto();
		return new ModelAndView("viajeConcepto", "viajeConceptoList", viajeConceptoList);
	}

	@RequestMapping("/saveViajeConcepto")
	public ModelAndView saveViajeConcepto(@ModelAttribute ViajeConcepto viajeConcepto) {
		logger.info("Guardando viajeConcepto: " + viajeConcepto);
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		
		if (viajeConcepto.getIdViajeConcepto() == Etiquetas.CERO) { // si el id es mayor a cero entonces se
			// trata de una edici�n
			viajeConcepto.setCreacionFecha(new Date());
			viajeConcepto.setCreacionUsuario(usuario.getIdUsuario());
			viajeConcepto.setActivo(Etiquetas.UNO_S);
			
			//BOOLEANO
	    	if(viajeConcepto.getEsCalculadoB()){ viajeConcepto.setEsCalculado(Etiquetas.UNO_S);}
	    	else { viajeConcepto.setEsCalculado(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getCalculoImporteDiarioB()){ viajeConcepto.setCalculoImporteDiario(Etiquetas.UNO_S);}
	    	else { viajeConcepto.setCalculoImporteDiario(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getCalculoDiasB()){ viajeConcepto.setCalculoDias(Etiquetas.UNO_S);}
	    	else { viajeConcepto.setCalculoDias(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getCalculoPersonasB()){ viajeConcepto.setCalculoPersonas(Etiquetas.UNO_S);}
	    	else { viajeConcepto.setCalculoPersonas(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getEsOtroB()){ viajeConcepto.setEsOtro(Etiquetas.UNO_S);}
	    	else { viajeConcepto.setEsOtro(Etiquetas.CERO_S);}
			
			viajeConceptoService.createViajeConcepto(viajeConcepto);
		} else {
			ViajeConcepto viajeConceptoEdicion = viajeConceptoService.getViajeConcepto(viajeConcepto.getIdViajeConcepto());
			viajeConceptoEdicion.setDescripcion(viajeConcepto.getDescripcion());
			viajeConceptoEdicion.setDolaresTarifa(viajeConcepto.getDolaresTarifa());
			viajeConceptoEdicion.setPesosTarifa(viajeConcepto.getPesosTarifa());
			viajeConceptoEdicion.setEsCalculado(viajeConcepto.getEsCalculado());
			viajeConceptoEdicion.setCalculoImporteDiario(viajeConcepto.getCalculoImporteDiario());
			viajeConceptoEdicion.setCalculoDias(viajeConcepto.getCalculoDias());
			viajeConceptoEdicion.setCalculoPersonas(viajeConcepto.getCalculoPersonas());
			viajeConceptoEdicion.setEsOtro(viajeConcepto.getEsOtro());
			
			//BOOLEANO
	    	if(viajeConcepto.getEsCalculadoB()){ viajeConceptoEdicion.setEsCalculado(Etiquetas.UNO_S);}
	    	else { viajeConceptoEdicion.setEsCalculado(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getCalculoImporteDiarioB()){ viajeConceptoEdicion.setCalculoImporteDiario(Etiquetas.UNO_S);}
	    	else { viajeConceptoEdicion.setCalculoImporteDiario(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getCalculoDiasB()){ viajeConceptoEdicion.setCalculoDias(Etiquetas.UNO_S);}
	    	else { viajeConceptoEdicion.setCalculoDias(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getCalculoPersonasB()){ viajeConceptoEdicion.setCalculoPersonas(Etiquetas.UNO_S);}
	    	else { viajeConceptoEdicion.setCalculoPersonas(Etiquetas.CERO_S);}
	    	if(viajeConcepto.getEsOtroB()){ viajeConceptoEdicion.setEsOtro(Etiquetas.UNO_S);}
	    	else { viajeConceptoEdicion.setEsOtro(Etiquetas.CERO_S);}
			
			viajeConceptoEdicion.setModificacionFecha(new Date());
			viajeConceptoEdicion.setModificacionUsuario(usuario.getIdUsuario());
			viajeConceptoService.updateViajeConcepto(viajeConceptoEdicion);
		}
		return new ModelAndView("redirect:viajeConcepto");
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getViajeConcepto", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		ViajeConcepto viajeConcepto = new ViajeConcepto();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			viajeConcepto = viajeConceptoService.getViajeConcepto(Integer.parseInt(intxnId));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("descripcion", viajeConcepto.getDescripcion());
		result.put("idViajeConcepto", String.valueOf(viajeConcepto.getIdViajeConcepto()));
		
		result.put("dolaresTarifa", String.valueOf(viajeConcepto.getDolaresTarifa()));
		result.put("pesosTarifa", String.valueOf(viajeConcepto.getPesosTarifa()));
		result.put("esCalculado", String.valueOf(viajeConcepto.getEsCalculado()));
		result.put("calculoImportediario", String.valueOf(viajeConcepto.getCalculoImporteDiario()));
		result.put("calculoDias", String.valueOf(viajeConcepto.getCalculoDias()));
		result.put("calculoPersonas", String.valueOf(viajeConcepto.getCalculoPersonas()));
		result.put("esOtro", String.valueOf(viajeConcepto.getEsOtro()));		

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
	@RequestMapping("/deleteViajeConcepto")
	public ModelAndView deleteViajeConcepto(@RequestParam Integer idViajeConcepto) throws UnsupportedEncodingException {
		StringBuilder st = new StringBuilder();

		try {
			viajeConceptoService.deleteViajeConcepto(idViajeConcepto);
		} catch (Exception e) {
			if (e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)) {
				logger.error(etiqueta.ERROR_DEPENDENCIAS, e);
				st.append("redirect:viajeConcepto");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DEPENDENCIAS,
						java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			} else {
				logger.error(etiqueta.ERROR_DELETE, e);
				st.append("redirect:viajeConcepto");
				st.append("?errorHead=");
				st.append(etiqueta.ERROR);
				st.append("&errorBody=");
				st.append(URLEncoder.encode(etiqueta.ERROR_DELETE, java.nio.charset.StandardCharsets.UTF_8.toString()));
				System.out.println(st.toString());
				return new ModelAndView(st.toString());
			}
		}
		return new ModelAndView("redirect:viajeConcepto");
	}

}