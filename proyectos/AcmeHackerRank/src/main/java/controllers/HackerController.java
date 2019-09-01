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

import services.ConfigurationService;
import services.HackerService;
import domain.Hacker;
import forms.HackerForm;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	@Autowired
	private HackerService hackerService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor -------------------------------------

	public HackerController() {
		super();
	}

	// Crear un nuevo Hacker
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		HackerForm hackerForm;

		// Traigo las makes
		String[] makes = this.configurationService.findConfiguration()
				.getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(
				Arrays.asList(makes));

		// Crear un hackerForm e inicializarlo con los terminos y condiciones
		hackerForm = new HackerForm();
		hackerForm.setCheckTerms(false);

		res = new ModelAndView("hacker/register");
		res.addObject("hackerForm", hackerForm);
		res.addObject("creditcardMakes", creditcardMakes);

		return res;
	}

	// Guardar un nuevo Hacker
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final HackerForm hackerForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Hacker hacker;

		// Traigo las makes
		String[] makes = this.configurationService.findConfiguration()
				.getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(
				Arrays.asList(makes));

		// Crear el objeto Hacker a partir del hackerForm
		hacker = this.hackerService.reconstruct(hackerForm, binding);

		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()) {
			res = new ModelAndView("hacker/register");
			res.addObject("creditcardMakes", creditcardMakes);
			// Si el formulario no tiene errores intenta guardarse
		} else {
			try {
				if (hacker.getId() != 0) {
					this.hackerService.save(hacker);
				} else {
					this.hackerService.saveFirst(hackerForm, binding);
				}
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", hacker.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("hacker/register");
				res.addObject("message", "actor.commit.error");
				res.addObject("creditcardMakes", creditcardMakes);
			}
		}
		return res;
	}

	// Editar hacker existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Hacker hacker;

		hacker = this.hackerService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("hacker/edit");
			result.addObject("hacker", hacker);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(hacker, "actor.commit.error");
		}

		return result;
	}

	// Actualizar hacker existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Hacker hacker, final BindingResult binding) {
		ModelAndView result;
		Hacker toSave;

		toSave = this.hackerService.reconstruct(hacker, binding);

		if (binding.hasErrors())
			result = new ModelAndView("hacker/edit");
		else
			try {
				this.hackerService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Hacker hacker) {
		final ModelAndView result = this.createEditModelAndView(hacker, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Hacker hacker,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("hacker", hacker);
		result.addObject("message", messagecode);

		return result;
	}

}
