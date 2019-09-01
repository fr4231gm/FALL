
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

import services.ActorService;
import services.ConfigurationService;
import services.CreditCardService;
import domain.Actor;
import domain.CreditCard;
import forms.CreditCardForm;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController {

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private ActorService			actorService;


	//Constructor ------------------------------------------------------
	public CreditCardController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		final Actor principal = this.actorService.findByPrincipal();
		final CreditCard cc = this.creditCardService.findCreditCardByActorId(principal.getId());
		try {
			final CreditCardForm ccForm = this.creditCardService.construct(cc);
			res = this.createEditModelAndView(ccForm);
		} catch (final Throwable Oops) {
			res = new ModelAndView();
		}
		return res;
	}

	//Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CreditCardForm ccForm, final BindingResult binding) {
		//Initialize variables
		ModelAndView res;
		CreditCard cc;
		cc = this.creditCardService.reconstruct2(ccForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(ccForm);
		else {
			//			try {
			this.creditCardService.save(cc);
			res = new ModelAndView("redirect:../");
			//			} catch (final Throwable oops) {
			//				res = this.createEditModelAndView(ccForm, "creditcard.commit.error.save");
			//			}
		}
		return res;
	}
	//Ancillary methods--------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreditCardForm ccf) {
		ModelAndView res;
		res = this.createEditModelAndView(ccf, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final CreditCardForm creditCardForm, final String messageCode) {
		ModelAndView res;
		final String[] makes = this.configService.findConfiguration().getMakes().split(",");
		//List<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		final Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		res = new ModelAndView("creditCard/edit");
		res.addObject("creditCardForm", creditCardForm);
		res.addObject("message", messageCode);
		res.addObject("creditcardMakes", creditcardMakes);
		return res;
	}
}
