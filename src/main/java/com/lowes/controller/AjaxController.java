package com.lowes.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AjaxController {

	@RequestMapping(value = "/testAjax1", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId, @RequestParam String message, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("Send Message UTF-8 ----------------- " + message);

        String json = null;
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("name", "test");
        result.put("message", message);
        result.put("time", "time");
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
}