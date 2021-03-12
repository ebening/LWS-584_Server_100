package com.lowes.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lowes.entity.Login;
 
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/cont")
public class RestController {
 
  public RestController(){
      System.out.println("init RestController");
  }
 
  //this method responses to GET request http://localhost../cont
  // return Login object as json
 
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody Login get(HttpServletResponse res) {
 
	  Login login = new Login();
	  login.setUsuario("josue");
	  login.setContrasena("contrasena");
 
      return login;
  }
 
  //this method response to POST request http://localhost/..login
  // receives json data sent by client --> map it to Login object
  // return Login object as json
  @RequestMapping(value="login", produces = "application/json",  method = RequestMethod.POST)
  public @ResponseBody Login post( @RequestBody final Login login) {   
	  
      System.out.println(login.getUsuario() + " " + login.getContrasena());
      return login;
      
  }
}