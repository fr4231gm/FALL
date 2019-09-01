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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ConfigurationService configurationService;

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Create A New Administrator
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		AdministratorForm administratorForm;

		String[] makes =  this.configurationService.findConfiguration().getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		
		administratorForm = new AdministratorForm();
		administratorForm.setCheckTerms(false);

		result = new ModelAndView("administrator/register");
		result.addObject("administratorForm", administratorForm);
		result.addObject("creditcardMakes", creditcardMakes);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(AdministratorForm administratorForm, BindingResult binding) {
		// Initialize Variables
		ModelAndView result;
		Administrator administrator;
		
		String[] makes =  this.configurationService.findConfiguration().getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		
		// Create the administrator object from the administratorForm
		administrator = this.administratorService.reconstruct(
				administratorForm, binding);

		// If the form has errors prints it
		if (binding.hasErrors()) {
			result = new ModelAndView("administrator/register");
			result.addObject("creditcardMakes", creditcardMakes);
		} else {
			// If the form does not have errors, try to save it
			try {
				if(administrator.getId()!=0){
					this.administratorService.save(administrator);
				}else{
					this.administratorService.saveFirst(administratorForm,binding);
				}
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
			} catch (Throwable oops) {
				result = new ModelAndView("administrator/register");
				result.addObject("message", "actor.commit.error");
				result.addObject("creditcardMakes", creditcardMakes);
			}
		}
		return result;

	}
	
	// Editar administrator existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("administrator/edit");
			result.addObject("administrator", administrator);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(administrator,
					"actor.commit.error");
		}

		return result;
	}

	// Actualizar administrator existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Administrator administrator,
			final BindingResult binding) {
		ModelAndView result;
		Administrator toSave;

		toSave = this.administratorService.reconstruct(administrator, binding);

		if (binding.hasErrors())
			result = new ModelAndView("administrator/edit");
		else
			try {
				this.administratorService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Administrator administrator) {
		final ModelAndView result = this.createEditModelAndView(administrator,
				null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Administrator administrator, final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("administrator", administrator);
		result.addObject("message", messagecode);

		return result;
	}
}
