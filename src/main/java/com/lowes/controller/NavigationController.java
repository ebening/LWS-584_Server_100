package com.lowes.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class NavigationController {

	public NavigationController(){
		
	}
	
	
	@RequestMapping("/ajax")
	public ModelAndView login(){
	 return new ModelAndView("ajax");
	}
	
	
	
}
