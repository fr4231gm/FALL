/*
 * LegalTermsController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/about-us")
public class AboutUsController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public AboutUsController(){
		super();
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(){
		ModelAndView res;
		res = new ModelAndView("about-us/index");
		return res;
	}
}
