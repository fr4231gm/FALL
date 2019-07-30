package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Sponsor;
import forms.SponsorForm;

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
		SponsorForm sponsorForm;

		sponsorForm = new SponsorForm();

		res = new ModelAndView("sposnor/register");
		res.addObject("sponsorForm", sponsorForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SponsorForm sponsorForm,
			final BindingResult binding) {

		ModelAndView res;
		Sponsor sponsor;
		
		
		sponsor = this.sponsorService.reconstruct(sponsorForm, binding);

		if (binding.hasErrors()){
			res = new ModelAndView("sponsor/register");
		}else
			try {
				if(sponsor.getId()!=0){
				this.sponsorService.save(sponsor);
				}else{
				this.sponsorService.saveFirst(sponsorForm, binding);
				}
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", sponsor.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("sponsor/register");
				res.addObject("message", "actor.commit.error");
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
	ModelAndView save(final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;
		Sponsor toSave;

		toSave = this.sponsorService.reconstruct(sponsor, binding);

		if (binding.hasErrors())
			result = new ModelAndView("sponsor/edit");
		else
			try {
				this.sponsorService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
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
