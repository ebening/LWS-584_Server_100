package com.lowes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowes.entity.Locacion;
import com.lowes.service.LocacionService;
import com.lowes.testing.Contact;
import com.lowes.testing.ContactForm;

import org.codehaus.jackson.map.ObjectMapper;
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

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ContactController {
	
	@Autowired
	private LocacionService locacionService;
  
    private static List<Contact> contacts = new ArrayList<Contact>();

    static {
        contacts.add(new Contact("Barack", "Obama", "barack.o@whitehouse.com", "147-852-965", new Locacion(1,"a")));
        contacts.add(new Contact("George", "Bush", "george.b@whitehouse.com", "785-985-652",new Locacion(2,"b")));
        contacts.add(new Contact("Bill", "Clinton", "bill.c@whitehouse.com", "236-587-412",new Locacion(3,"c")));
        contacts.add(new Contact("Ronald", "Reagan", "ronald.r@whitehouse.com", "369-852-452",new Locacion(4,"d")));
        contacts.add(new Contact("Josue", "Sanchez", "velaz2@hh.com", "777-777-777",new Locacion(5,"e")));
    }
     
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get() {
         
        ContactForm contactForm = new ContactForm();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
        contactForm.setContacts(contacts);
		model.put("contactForm", contactForm); 
		
		List<Locacion> lc = locacionService.getAllLocaciones();
		model.put("lstLocaciones", lc);

         
        return new ModelAndView("add_contact" ,model);
    }
     
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("contactForm") ContactForm contactForm) {
        System.out.println(contactForm);
        System.out.println(contactForm.getContacts());
        List<Contact> contacts = contactForm.getContacts();
         
        if(null != contacts && contacts.size() > 0) {
            ContactController.contacts = contacts;
            for (Contact contact : contacts) {
                System.out.printf("%s \t %s \n", contact.getFirstname(), contact.getLastname());
            }
        }
         
        return new ModelAndView("show_contact", "contactForm", contactForm);
    }
    
    
    
    
    @RequestMapping(value = "/getContactR", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    ResponseEntity<String> sendDataEdit(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
	    String json = null;
	    ContactForm contactForm = new ContactForm();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
        contacts.add(new Contact("Josue111", "Sanchez", "velaz2@hh.com", "777-777-777",new Locacion(5,"e")));

        contactForm.setContacts(contacts);
        result.put("contactForm", contactForm); 
		
		List<Locacion> lc = locacionService.getAllLocaciones();
		result.put("lstLocaciones", lc);
		

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
    
    
    @RequestMapping(value = "/getContactR2", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    ResponseEntity<String> sendDataEdit2(HttpSession session,@RequestParam Integer numrows, HttpServletRequest request, HttpServletResponse response) {
		
	    String json = null;
	    String result = null;
	    StringBuilder row = new StringBuilder();
	    
		List<Locacion> lc = locacionService.getAllLocaciones();

	    
	    row.append("<tr>");
        row.append("<td align=\"center\">1</td>");
        row.append("<td><input id=\"contacts0.firstname\" name=\"contacts[0].firstname\" value=\"\" type=\"text\"></td>");
        row.append("<td><input id=\"contacts0.lastname\" name=\"contacts[0].lastname\" value=\"\" type=\"text\"></td>");
        row.append("<td><input id=\"contacts0.email\" name=\"contacts[0].email\" value=\"\" type=\"text\"></td>");
        row.append("<td><input id=\"contacts0.phone\" name=\"contacts[0].phone\" value=\"147-852-965\" type=\"text\"></td>");
        row.append("<td>");
        row.append(" <select id=\"contacts0.locacion\" name=\"contacts[0].locacion\" class=\"form-control\">");
          						    row.append("<option value=\"-1\">Seleccione:</option>");
          						    for(Locacion loc : lc){
          						    	row.append("<option value=\"");
          						    	row.append(String.valueOf(loc.getIdLocacion()));
          						    	row.append("\"> "+ loc.getNumeroDescripcionLocacion() +" </option>");
          						    }
									
		    row.append("</select>");
		   row.append("</td>");
		row.append("</tr>");
    
	    
	
		

	     ObjectMapper map = new ObjectMapper();
	        if (!row.toString().isEmpty()) {
	            try {
	                json = map.writeValueAsString(row.toString());
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        //respuesta
	        HttpHeaders responseHeaders = new HttpHeaders(); 
	        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
    	
    }
    
    private List<Locacion> getLocacionesPermitidasPorUsuario(){
    	
    	
    	return null;
    }
    
    
}