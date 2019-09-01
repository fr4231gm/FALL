package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
@RequestMapping("/creditcard")
public class CreditCardController extends AbstractController {

	// Servicios
	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private ConfigurationService configService;

	@Autowired
	private ActorService actorService;

	// Constructor
	public CreditCardController() {
		super();
	}

	// Show
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView res;
		Actor principal = this.actorService.findByPrincipal();

		CreditCardForm creditCardForm;

		try {
			CreditCard cc = this.creditCardService
					.findCreditCardByActorId(principal.getId());
			creditCardForm = this.creditCardService.construct(cc);
			res = new ModelAndView("creditcard/display");
			res.addObject("creditCardForm", creditCardForm);
		} catch (final Throwable Oops) {
			res = new ModelAndView("creditcard/notDisplay");
		}

		return res;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final CreditCardForm ccForm = new CreditCardForm();
		final Actor principal = this.actorService.findByPrincipal();
		try {
			ccForm.setId(0);
			ccForm.setActorId(principal.getId());
			res = this.createEditModelAndView(ccForm);
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}
		return res;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		CreditCard cc;
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.creditCardService
				.findCreditCardByActorId(principal.getId()).getActor().getId() == principal
				.getId());
		try {
			cc = this.creditCardService.findCreditCardByActorId(principal
					.getId());
			Assert.notNull(cc);
			CreditCardForm ccForm = this.creditCardService.construct(cc);
			res = this.createEditModelAndView(ccForm);
		} catch (final Throwable Oops) {
			res = new ModelAndView();
		}
		return res;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save( final CreditCardForm ccForm,
			final BindingResult binding) {
		ModelAndView res;
		CreditCard cc = this.creditCardService.reconstruct(ccForm, binding);
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(ccForm);
		} else {
			try {
				this.creditCardService.save(cc);
				res = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(ccForm,
						"creditcard.commit.error.save");
			}
		}
		return res;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView res;
		Actor principal = this.actorService.findByPrincipal();
		try {
			Assert.notNull(this.creditCardService
					.findCreditCardByActorId(principal.getId()));
			this.creditCardService.delete();
			res = new ModelAndView("redirect:display.do");
		} catch (final IllegalArgumentException oops) {
			CreditCard cc = this.creditCardService
					.findCreditCardByActorId(principal.getId());
			CreditCardForm creditCardForm = this.creditCardService
					.construct(cc);
			res = new ModelAndView("creditcard/display");
			res.addObject("creditCardForm", creditCardForm);
			res.addObject("message", "creditcard.commit.error.delete");
		} catch (final Throwable oops) {
			CreditCard cc = this.creditCardService
					.findCreditCardByActorId(principal.getId());
			CreditCardForm creditCardForm = this.creditCardService
					.construct(cc);
			res = new ModelAndView("creditcard/display");
			res.addObject("creditCardForm", creditCardForm);
			res.addObject("message", "creditcard.commit.error.delete");
		}
		return res;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final CreditCardForm ccf) {
		ModelAndView res;
		res = this.createEditModelAndView(ccf, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(
			final CreditCardForm creditCardForm, final String messageCode) {

		ModelAndView res;
		final String[] makes = this.configService.findConfiguration()
				.getMakes().split(",");
		final Collection<String> creditcardMakes = new ArrayList<>(
				Arrays.asList(makes));
		res = new ModelAndView("creditcard/edit");
		res.addObject("creditCardForm", creditCardForm);
		res.addObject("message", messageCode);
		res.addObject("creditcardMakes", creditcardMakes);

		return res;
	}

}
