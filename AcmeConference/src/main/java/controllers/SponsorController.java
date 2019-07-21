package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Sponsor;

import services.SponsorService;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	private SponsorService sponsorService;

	// Constructor -------------------------------------

	public SponsorController() {
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		ModelAndView res;

		Sponsor sponsor = new Sponsor();

		res = new ModelAndView("sponsor/register");
		res.addObject("sponsor", sponsor);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Sponsor sponsor,
			final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {
			res = new ModelAndView("sponsor/register");
			
		} else {
			
			try {
				
					this.sponsorService.save(sponsor);
					
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				
			} catch (final Throwable opps) {
				
				res = new ModelAndView("sponsor/register");
				res.addObject("message", "actor.commit.error");
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

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
	ModelAndView edit(final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("sponsor/edit");
		else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("welcome/index");

				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor,
						"actor.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		final ModelAndView result = this.createEditModelAndView(sponsor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("sponsor", sponsor);
		result.addObject("message", messagecode);

		return result;
	}

}
