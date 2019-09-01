/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Brotherhood;
import services.ActorService;
import services.BrotherhoodService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}
	
	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false) String name, 
			@RequestParam(required = false) String title) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Brotherhood principal2;
		
		try {
			Actor principal = this.actorService.findByPrincipal();
			if(principal != null){
				name = principal.getName();
				if(principal instanceof Brotherhood){
					principal2 = this.brotherhoodService.findByPrincipal();
					title = principal2.getTitle();
				}
			}

		}catch (Throwable oops){
			
		}

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("title", title);
		result.addObject("moment", moment);

		return result;
	}
}
