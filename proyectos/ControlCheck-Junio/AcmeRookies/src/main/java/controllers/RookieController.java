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
import services.CreditCardService;
import services.RookieService;
import domain.CreditCard;
import domain.Rookie;
import forms.RookieForm;

@Controller
@RequestMapping("/rookie")
public class RookieController extends AbstractController {

	@Autowired
	private RookieService rookieService;

	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private CreditCardService creditCardService;

	// Constructor -------------------------------------

	public RookieController() {
		super();
	}

	// Crear un nuevo Rookie
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		RookieForm rookieForm;

		// Traigo las makes
		String[] makes = this.configurationService.findConfiguration()
				.getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(
				Arrays.asList(makes));

		// Crear un rookieForm e inicializarlo con los terminos y condiciones
		rookieForm = new RookieForm();
		rookieForm.setCheckTerms(false);

		res = new ModelAndView("rookie/register");
		res.addObject("rookieForm", rookieForm);
		res.addObject("creditcardMakes", creditcardMakes);

		return res;
	}

	// Guardar un nuevo Rookie
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RookieForm rookieForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Rookie rookie;

		// Traigo las makes
		String[] makes = this.configurationService.findConfiguration()
				.getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(
				Arrays.asList(makes));

		// Crear el objeto Rookie a partir del rookieForm
		rookie = this.rookieService.reconstruct(rookieForm, binding);

		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()) {
			res = new ModelAndView("rookie/register");
			res.addObject("creditcardMakes", creditcardMakes);
			// Si el formulario no tiene errores intenta guardarse
		} else {
			try {
				if (rookie.getId() != 0) {
					this.rookieService.save(rookie);
				} else {
					Rookie r = this.rookieService.reconstruct(rookieForm, binding);
					CreditCard c = this.creditCardService.reconstruct(rookieForm, binding);
					this.rookieService.saveFirst(r, c);
				}
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", rookie.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("rookie/register");
				res.addObject("message", "actor.commit.error");
				res.addObject("creditcardMakes", creditcardMakes);
			}
		}
		return res;
	}

	// Editar rookie existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Rookie rookie;

		rookie = this.rookieService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("rookie/edit");
			result.addObject("rookie", rookie);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rookie, "actor.commit.error");
		}

		return result;
	}

	// Actualizar rookie existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Rookie rookie, final BindingResult binding) {
		ModelAndView result;
		Rookie toSave;

		toSave = this.rookieService.reconstruct(rookie, binding);

		if (binding.hasErrors())
			result = new ModelAndView("rookie/edit");
		else
			try {
				this.rookieService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Rookie rookie) {
		final ModelAndView result = this.createEditModelAndView(rookie, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Rookie rookie,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("rookie", rookie);
		result.addObject("message", messagecode);

		return result;
	}

}
