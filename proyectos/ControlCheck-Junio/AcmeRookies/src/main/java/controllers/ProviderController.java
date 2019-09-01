
package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CreditCardService;
import services.ProviderService;
import domain.CreditCard;
import domain.Provider;
import forms.ProviderForm;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private ConfigurationService	configurationService;
	
	@Autowired
	private CreditCardService		creditCardService;



	// Constructors -----------------------------------------------------------

	public ProviderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Provider> providers;

		providers = this.providerService.findAll();

		result = new ModelAndView("provider/list");
		result.addObject("providers", providers);
		result.addObject("requestURI", "provider/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"providerId"
	})
	public ModelAndView display(@RequestParam final int providerId) {
		ModelAndView result;

		// Initialize variables
		Provider provider;

		provider = this.providerService.findOne(providerId);

		result = new ModelAndView("provider/display");
		result.addObject("provider", provider);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;
		ProviderForm providerForm;

		final String[] makes = this.configurationService.findConfiguration().getMakes().split(",");
		final Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));

		providerForm = new ProviderForm();
		providerForm.setCheckTerms(false);

		res = new ModelAndView("provider/register");
		res.addObject("providerForm", providerForm);
		res.addObject("creditcardMakes", creditcardMakes);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView save(final ProviderForm form, final BindingResult binding) {
		ModelAndView res;
		Provider provider;

		final String[] makes = this.configurationService.findConfiguration().getMakes().split(",");
		final Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));

		provider = this.providerService.reconstruct(form, binding);
		CreditCard c = this.creditCardService.reconstruct(form, binding);
		if (binding.hasErrors()) {
			res = new ModelAndView("provider/register");
			res.addObject("creditcardMakes", creditcardMakes);
		} else
			try {
				if (provider.getId() != 0)
					this.providerService.save(provider);
				else{
					this.providerService.saveFirst(provider, c);
				}
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", provider.getName());
			} catch (final Throwable oops) {
				res = new ModelAndView("provider/register");
				res.addObject("message", "actor.commit.error");
				res.addObject("creditcardMakes", creditcardMakes);
			}

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		Provider provider;

		provider = this.providerService.findOneTrimmedByPrincipal();
		try {
			res = new ModelAndView("provider/edit");
			res.addObject("provider", provider);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(provider, "actor.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final Provider provider, final BindingResult binding) {
		ModelAndView res;
		Provider toSave;

		toSave = this.providerService.reconstruct(provider, binding);

		if (binding.hasErrors())
			res = new ModelAndView("provider/edit");
		else
			try {
				this.providerService.save(toSave);
				res = new ModelAndView("welcome/index");
				res.addObject("name", toSave.getName());
				res.addObject("exitCode", "actor.edit.success");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(toSave, "actor.commit.error");
			}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Provider provider) {
		final ModelAndView res = this.createEditModelAndView(provider, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Provider provider, final String messagecode) {
		ModelAndView res;

		res = new ModelAndView();
		res.addObject("provider", provider);
		res.addObject("message", messagecode);
		return res;
	}

}
