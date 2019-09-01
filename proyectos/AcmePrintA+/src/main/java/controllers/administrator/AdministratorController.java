/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;

import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Methods ---------------------------------------------------------------

	// Crear un nuevo Administrator
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		AdministratorForm administratorForm;

		// Crear un administratorForm e inicializarlo con los terminos y
		// condiciones
		administratorForm = new AdministratorForm();
		administratorForm.setCheckTerms(false);

		res = new ModelAndView("administrator/register");
		res.addObject("administratorForm", administratorForm);

		return res;
	}

	// Guardar un nuevo Administrator
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdministratorForm administratorForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Administrator administrator;

		// Crear el objeto Administrator a partir del administratorForm
		administrator = this.administratorService.reconstruct(
				administratorForm, binding);

		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()) {
			res = new ModelAndView("administrator/register");
			// Si el formulario no tiene errores intenta guardarse
		} else {
			try {
				this.administratorService.save(administrator);

				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", administrator.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("administrator/register");
				res.addObject("message", "actor.commit.error");
			}
		}
		return res;
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
			result = this
					.createEditModelAndView(administrator, "actor.commit.error");
		}

		return result;
	}

	// Actualizar administrator existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Administrator administrator, final BindingResult binding) {
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

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		final ModelAndView result = this.createEditModelAndView(administrator, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("administrator", administrator);
		result.addObject("message", messagecode);

		return result;
	}

}
