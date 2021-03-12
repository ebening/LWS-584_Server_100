package com.lowes.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Solicitud;
import com.lowes.service.SolicitudService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ComprobacionController {
	
	@Autowired
	private SolicitudService solicitudService;
	
	@RequestMapping(value = "/comprobacionAnticipasasdo", method = RequestMethod.GET)
	private ModelAndView comprobacionAnticipo(HttpSession session, Integer id){

		List<Solicitud> solicitudes = solicitudService.getAllSolicitud();
		
		return new ModelAndView("comprobacionAnticipo");

	}
	
	  // metodo ajax para el cambio de estatus a cancelada.
	  @RequestMapping(value = "/cancelarSolicitudComprobacionAjax", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    private @ResponseBody ResponseEntity<String> cancelarSolicitudComprobacion(HttpSession session, @RequestParam Integer idSolicitud, HttpServletRequest request, HttpServletResponse response) {
		  
		  String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        
	        //bind json
	        ObjectMapper map = new ObjectMapper();
	        if (!result.isEmpty()) {
	            try {
	                json = map.writeValueAsString(result);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        //respuesta
	        HttpHeaders responseHeaders = new HttpHeaders(); 
	        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	  }



}
