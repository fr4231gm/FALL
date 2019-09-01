
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CreditCardService;
import domain.Application;
import domain.CreditCard;

@Controller
@RequestMapping("/creditcard/customer")
public class CreditCardController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public CreditCardController() {
		super();
	}

	// Show
	//
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView findByCreditCardId(@RequestParam final int creditCardId) {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.findOne(creditCardId);
		Assert.notNull(creditCard);

		result = new ModelAndView("creditcard/customer/show");
		result.addObject("creditcard", creditCard);
		result.addObject("requestURI", "creditcard/customer/show.do?creditCardId=" + creditCardId);

		return result;
	}

	// Create 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		CreditCard creditCard;
		creditCard = this.creditCardService.create();
		result = this.createEditModelAndView(creditCard);
		return result;
	}
	
	@RequestMapping(value = "/createforapp", method = RequestMethod.GET)
	public ModelAndView createforapp(@Valid final Application application) {
		final ModelAndView result;

		CreditCard creditCard;
		creditCard = this.creditCardService.create();
		result = this.createEditModelAndView(creditCard);
		return result;
	}
	
	// Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard);
		else
			try {
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:PONER AQUÍ LA REDIREÇAO");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, "creditcard.commit.error");
			}

		return result;
	}
	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String messageCode) {
		final ModelAndView result = new ModelAndView("creditcard/customer/create");
		Assert.notNull(creditCard);
		// actionURI 
		final Collection<String> prueba = new ArrayList<String>();
		result.addObject("brandname", prueba);
		result.addObject("actionURI", "creditcard/customer/create.do");
		result.addObject("creditCard", creditCard);
		result.addObject("message", messageCode);
		result.addObject("configuration", configurationService);
		return result;
	}

}
