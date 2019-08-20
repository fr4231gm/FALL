
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	//List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsBySponsorId();
		res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsorship/sponsor/list.do");
		return res;
	}

	//Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		final Sponsorship sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
		final Sponsor principal = this.sponsorService.findByPrincipal();

		if (sponsorship == null)
			res = new ModelAndView("security/notfind");
		else if (sponsorship.getSponsor().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = new ModelAndView("sponsorship/display");
				res.addObject("sponsorship", sponsorship);
			} catch (final Throwable oops) {
				res = new ModelAndView("security/notfind");
			}
		return res;
	}

	// Editar sponsorship existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {

		ModelAndView result;
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		try {
			result = new ModelAndView("sponsorship/edit");
			result.addObject("sponsorship", sponsorship);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "actor.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		final ModelAndView result = this.createEditModelAndView(sponsorship, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("sponsorship", sponsorship);
		result.addObject("message", messagecode);

		return result;
	}

	// Actualizar sponsorship existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;
		final Sponsorship toSave = null;

		if (binding.hasErrors())
			result = new ModelAndView("sponsorship/edit");
		else
			try {
				this.sponsorshipService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave, "actor.commit.error");
			}

		return result;
	}

}
