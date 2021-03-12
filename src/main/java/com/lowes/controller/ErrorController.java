package com.lowes.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.SQLGrammarException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.lowes.service.ParametroService;
import com.lowes.util.Etiquetas;

/**
 * @author miguelr
 *
 */
@ControllerAdvice
public class ErrorController {
	private static final Logger logger = Logger.getLogger(ErrorController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private ParametroService parametroService;
	
	public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * @author miguelr
     * @param e
     * @param req
     * @return ModelAndView
     * DEFAULT HANDLER.
     */
	@ExceptionHandler(Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.info("Se ha disparado una excepción");
		logger.error(e);
		if(Integer.parseInt(parametroService.getParametroByName("errorHandler").getValor()) == 1){
			HashMap<String, Object> modelo = new HashMap<>();
			modelo.put("error", e);
			if (e.getClass() == NullPointerException.class) {
				modelo.put("msg1", "NullPointerException!");
				modelo.put("msg2", "¿Como has llegado aquí?");
				modelo.put("msg3", "No tienes acceso a este recurso.");
			} else if (e.getClass() == NoHandlerFoundException.class) {
				modelo.put("msg1", "NoHandlerFoundException!");
				modelo.put("msg2", "¿Como has llegado aquí?");
				modelo.put("msg3", "No tienes acceso a este recurso.");
			} else if (e.getClass() == SQLGrammarException.class) {
				modelo.put("msg1", "SQLGrammarException!");
				modelo.put("msg2", "¿Como has llegado aquí?");
				modelo.put("msg3", "No tienes acceso a este recurso.");
			} else {
				modelo.put("msg1", "Error Default!");
				modelo.put("msg2", "¿Como has llegado aquí?");
				modelo.put("msg3", "No tienes acceso a este recurso.");
			}
			return new ModelAndView("error", modelo);
		}else{
			throw e;
		}
	}
	
//	@ExceptionHandler(Throwable.class)
//	public ModelAndView IndexOutOfBoundsException(HttpServletRequest req, Throwable e) throws Throwable {
//		// Otherwise setup and send the user to a default error-view.
//		logger.info("Se ha disparado un Throwable");
//		logger.error(e);
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("exception", e);
//		mav.addObject("url", req.getRequestURL());
//		mav.setViewName(DEFAULT_ERROR_VIEW);
//
//		HashMap<String, Object> modelo = new HashMap<>();
//		if (e != null) {
//			// objetos para la vista
//			modelo.put("error", e);
//
//			modelo.put("msg1", "Opps!");
//			modelo.put("msg2", "¿Como has llegado aquí?");
//			modelo.put("msg3", "No tienes acceso a este recurso.");
//		}
//		return new ModelAndView("error", modelo);
//	}
}