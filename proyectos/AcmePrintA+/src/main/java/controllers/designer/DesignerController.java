/*
 * DesignerController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.designer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DesignerService;
import controllers.AbstractController;
import domain.Designer;
import forms.DesignerForm;

@Controller
@RequestMapping("/designer")
public class DesignerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private DesignerService designerService;

	// Constructors -----------------------------------------------------------

	public DesignerController() {
		super();
	}

	// Methods ---------------------------------------------------------------

	// Crear un nuevo Designer
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		DesignerForm designerForm;

		// Crear un designerForm e inicializarlo con los terminos y condiciones
		designerForm = new DesignerForm();
		designerForm.setCheckTerms(false);

		res = new ModelAndView("designer/register");
		res.addObject("designerForm", designerForm);

		return res;
	}

	// Guardar un nuevo Designer
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final DesignerForm designerForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Designer designer;

		// Crear el objeto Designer a partir del designerForm
		designer = this.designerService.reconstruct(designerForm, binding);

		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()) {
			res = new ModelAndView("designer/register");
			// Si el formulario no tiene errores intenta guardarse
		} else {
			try {
				this.designerService.save(designer);
				
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", designer.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("designer/register");
				res.addObject("message", "actor.commit.error");
			}
		}
		return res;
	}

	// Editar designer existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Designer designer;

		designer = this.designerService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("designer/edit");
			result.addObject("designer", designer);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(designer, "actor.commit.error");
		}

		return result;
	}

	// Actualizar designer existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Designer designer, final BindingResult binding) {
		ModelAndView result;
		Designer toSave;

		toSave = this.designerService.reconstruct(designer, binding);

		if (binding.hasErrors())
			result = new ModelAndView("designer/edit");
		else
			try {
				this.designerService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
			"designerId"
		})
		public ModelAndView display(@RequestParam final int designerId) {
			ModelAndView result;

			// Initialize variables
			Designer designer;

			designer = this.designerService.findOne(designerId);

			result = new ModelAndView("designer/display");
			result.addObject("designer", designer);

			return result;
		}

	protected ModelAndView createEditModelAndView(final Designer designer) {
		final ModelAndView result = this.createEditModelAndView(designer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Designer designer,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("designer", designer);
		result.addObject("message", messagecode);

		return result;
	}
}
