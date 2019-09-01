
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import domain.Sponsor;
import forms.SponsorForm;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	// Services
	@Autowired
	private SponsorService	sponsorService;


	// Constructors

	public SponsorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"sponsorId"
	})
	public ModelAndView display(final int sponsorId) {
		ModelAndView res;

		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(sponsorId);

		res = new ModelAndView("sponsor/display");
		res.addObject("sponsor", sponsor);
		return res;
	}

	// Create A New Sponsor
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		SponsorForm sponsorForm;

		sponsorForm = new SponsorForm();
		sponsorForm.setCheckTerms(false);

		result = new ModelAndView("sponsor/register");
		result.addObject("sponsorForm", sponsorForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(final SponsorForm sponsorForm, final BindingResult binding) {
		// Initialize Variables
		ModelAndView result;
		Sponsor sponsor;

		// Create the sponsor object from the sponsorForm
		sponsor = this.sponsorService.reconstruct(sponsorForm, binding);

		// If the form has errors prints it
		if (binding.hasErrors())
			result = new ModelAndView("sponsor/register");
		else
			// If the form does not have errors, try to save it
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
				result.addObject("name", sponsor.getName());
			} catch (final Throwable oops) {
				result = new ModelAndView("sponsor/register");
				result.addObject("message", "actor.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("sponsor/edit");
			result.addObject("sponsor", sponsor);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsor, "actor.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;
		Sponsor toSave;
		SimpleDateFormat formatter;
		String moment;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		toSave = this.sponsorService.reconstruct(sponsor, binding);

		if (binding.hasErrors())
			result = new ModelAndView("sponsor/edit");
		else
			try {
				this.sponsorService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("moment", moment);
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave, "actor.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		ModelAndView res;

		res = this.createEditModelAndView(sponsor, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("sponsor/edit");

		res.addObject("message", messageCode);

		return res;
	}

}
