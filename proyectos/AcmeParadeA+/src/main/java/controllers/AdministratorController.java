/*
 * AdministratorController.java
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;

import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}
	
	// Create A New Administrator
	@RequestMapping(value = "/register", 
			method = RequestMethod.GET)
	ModelAndView register(){
		ModelAndView result;
		AdministratorForm administratorForm;
		
		administratorForm = new AdministratorForm();
		administratorForm.setCheckTerms(false);
		
		result = new ModelAndView("administrator/register");
		result.addObject("administratorForm", administratorForm);
		return result;
	}
	
	@RequestMapping(value = "/register", 
			method = RequestMethod.POST,
			params = "save")
	ModelAndView save(AdministratorForm administratorForm, BindingResult binding){
		//Initialize Variables
		ModelAndView result;
		Administrator administrator;
		
		//Create the administrator object from the administratorForm
		administrator = this.administratorService.reconstruct(administratorForm, binding);
		
		//If the form has errors prints it
		if(binding.hasErrors()){
			result = new ModelAndView("administrator/register");
		} else {
		//If the form does not have errors, try to save it
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");		
			} catch (Throwable oops){
				result = new ModelAndView("administrator/register");
				result.addObject("message", "actor.commit.error");	
			}
		}
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;
		result = new ModelAndView("administrator/edit");

		try {
			administrator = this.administratorService.findOneTrimmedByPrincipal();
			result.addObject("administrator", administrator);
		} catch (Throwable oops) {
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(Administrator administrator, BindingResult binding) {
		ModelAndView result;
		Administrator toSave;
		SimpleDateFormat formatter;
		String moment;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		toSave = this.administratorService.reconstruct(administrator, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("administrator/edit");
		} else {
			try {
				this.administratorService.save(toSave);
				result = new ModelAndView("welcome/index");
				
				result.addObject("name", toSave.getName());
				result.addObject("moment", moment);
				result.addObject("exitCode", "actor.edit.success");
				
			} catch (Throwable oops) {
				result = new ModelAndView("administrator/edit");
				result.addObject("message", "actor.commit.error");
			}
		}

		return result;
	}

}
