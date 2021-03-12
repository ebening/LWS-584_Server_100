package com.lowes.controller;

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

import com.lowes.entity.TestHibernate;
import com.lowes.service.TestHibernateService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class TestHibernateController {
     
	private static final Logger logger = Logger.getLogger(TestHibernateController.class);
	
	@Autowired
	private TestHibernateService testHibernateService;
	
	
	// metodo de listado.
	@RequestMapping("testHibernate")
	public ModelAndView testHibernate(@ModelAttribute TestHibernate testHibernate){
		 List<TestHibernate> testHibernateList = testHibernateService.getAllTestHibernate();
	     return new ModelAndView("testHibernate", "testHibernateList", testHibernateList);
	}
	
	  // metodo que inserta y actualiza en la base de datos.
	  @RequestMapping("saveTestHibernate")
	    public ModelAndView saveTestHibernate(@ModelAttribute TestHibernate testHibernate) {
	    	logger.info("Guardando test: "+testHibernate);
	        if(testHibernate.getId() == 0){ // si el id es mayor a cero entonces se trata de una edici�n
	        	testHibernateService.createTestHibernate(testHibernate);
	        } else {
	        	testHibernateService.updateTestHibernate(testHibernate);
	        }
	        return new ModelAndView("redirect:testHibernate");
	    }
	
	
	  
	  // metodo ajax para la carga dinamica de edicion.
	  @RequestMapping(value = "/getTestHibernate", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody
	    ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId, HttpServletRequest request, HttpServletResponse response) {

	        //System.out.println("Send Message UTF-8 ----------------- " + intxnId);
	        
	        //objeto de respuesta
	        TestHibernate test1 =  new TestHibernate();
	        
	        // consulta 
	        if(Integer.parseInt(intxnId) > 0){
	        	test1 = testHibernateService.getTestHibernate(Integer.parseInt(intxnId));
	        }

	        String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        
	        //seteando la respuesta
	        result.put("nombre", test1.getNombre());
	        result.put("precio", test1.getPrecio().toString());
	        result.put("id", String.valueOf(test1.getId()));
	        
	        ObjectMapper map = new ObjectMapper();
	        if (!result.isEmpty()) {
	            try {
	                json = map.writeValueAsString(result);
	                //System.out.println("Send Message  :::::::: : " + json);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        HttpHeaders responseHeaders = new HttpHeaders(); 
	        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	    }
	  
	  
	    // metodo para eliminar
	    @RequestMapping("deleteTestHibernate")
	    public ModelAndView deleteEmployee(@RequestParam Integer id) {
	    	testHibernateService.deleteTestHibernate(id);
	        return new ModelAndView("redirect:testHibernate");
	    }
	
}
