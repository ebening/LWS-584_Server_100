package com.lowes.controller;


import javax.servlet.http.HttpServletRequest;
 

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Users;
 
 
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class SpringMVCController {
	
	
	@RequestMapping(value = "/helloWorld.web", method = RequestMethod.GET)
	public ModelAndView printWelcome(@ModelAttribute("user") Users user) {
 
		ModelAndView mav = new ModelAndView("SubmitForm");
		mav.addObject("message", "Hello World!!!");
		return mav;
 
	}
 
	@RequestMapping(value="/submitForm.web", method = RequestMethod.POST)
    public @ResponseBody Users  submittedFromData(@RequestBody Users user, HttpServletRequest request) {	
		return user;
	}	
	
}