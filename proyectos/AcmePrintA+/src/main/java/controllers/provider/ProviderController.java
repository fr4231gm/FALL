package controllers.provider;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import controllers.AbstractController;
import domain.Provider;
import forms.ProviderForm;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ProviderService providerService;


	// Constructor ------------------------------------------------------------

	public ProviderController() {
		super();
	}

	// Crear una nueva Provider
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		ProviderForm providerForm;

		// Crear un providerForm e inicializarlo con los terminos y condiciones
		providerForm = new ProviderForm();
		providerForm.setCheckTerms(false);

		res = new ModelAndView("provider/register");
		res.addObject("providerForm", providerForm);

		return res;
	}

	// Guardar una nueva Provider
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProviderForm providerForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Provider provider;

		// Crear el objeto Provider a partir del providerForm
		provider = this.providerService.reconstruct(providerForm, binding);
		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()) {
			res = new ModelAndView("provider/register");
			// Si el formulario no tiene errores intenta guardarse
		} else
			try {

				this.providerService.save(provider);

				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", provider.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("provider/register");
				res.addObject("message", "actor.commit.error");
			}
		return res;
	}

	// Editar provider existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Provider provider;

		provider = this.providerService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("provider/edit");
			result.addObject("provider", provider);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(provider, "actor.commit.error");
		}

		return result;
	}

	// Actualizar provider existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Provider provider, final BindingResult binding) {
		ModelAndView result;
		Provider toSave;

		toSave = this.providerService.reconstruct(provider, binding);

		if (binding.hasErrors())
			result = new ModelAndView("provider/edit");
		else
			try {
				this.providerService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Provider> companies;

		companies = this.providerService.findAll();

		result = new ModelAndView("provider/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "provider/list.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final Provider provider) {
		final ModelAndView result = this.createEditModelAndView(provider, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Provider provider,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("provider", provider);
		result.addObject("message", messagecode);

		return result;
	}

}
