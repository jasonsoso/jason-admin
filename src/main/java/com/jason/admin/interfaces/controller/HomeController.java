package com.jason.admin.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String admin(){
		return "admin/index";
	}
	
	@RequestMapping(value = "/admin/deny/")
	public String deny() {
		return "admin/deny";
	}


}

