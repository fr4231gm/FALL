package controllers;

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

import services.ActorService;
import services.ConfigurationService;
import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialprofile")
public class SocialProfileController extends AbstractController {

	// AQUI VAN LOS SERVISIOS QUE NOS HAGA FALTA
	// listar, crear y borrar

	@Autowired
	SocialProfileService socialProfileService;

	@Autowired
	ActorService actorService;
	
	@Autowired
	private ConfigurationService	configService;

	public SocialProfileController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId) {
		ModelAndView result;
		Collection<SocialProfile> social;

		social = this.socialProfileService.findByActor(actorId);
		Assert.notNull(social);

		result = new ModelAndView("socialprofile/list");
		result.addObject("id", actorId);
		result.addObject("social", social);
		result.addObject("requestURI", "socialprofile/list.do");
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}

	// Show

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showSocial(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile social;
		social = this.socialProfileService.findOne(socialProfileId);
		result = new ModelAndView("socialprofile/show");
		result.addObject("socialprofile", social);
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		ModelAndView result;

		SocialProfile socialProfile;
		socialProfile = this.socialProfileService.create();
		socialProfile.setActor(this.actorService.findOne(actorId));
		result = this.createEditModelAndView(socialProfile);
		return result;

	}

	// Edito
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile social;
		try {
			social = this.socialProfileService.findOne(socialProfileId);
			Assert.notNull(social);
			result = this.createEditModelAndView(social);
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;

	}

	// Delete

	@RequestMapping(value = "/remove", method = RequestMethod.GET, params = { "id" })
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		SocialProfile sp = this.socialProfileService.findOne(id);
		try {
			Assert.isTrue(sp.getActor().getId() == this.actorService
					.findByPrincipal().getId());
			this.socialProfileService.delete(sp);
			result = new ModelAndView("redirect:list.do?actorId="
					+ this.actorService.findByPrincipal().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile social, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(social);
		else 
			try {
				this.socialProfileService.save(social);
				result = new ModelAndView("redirect:list.do?actorId=" + social.getActor().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		return result;
		}
	

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final SocialProfile social,
			final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(social);
		else
			try {
				this.socialProfileService.save(social);
				result = new ModelAndView("redirect:list.do?actorId="
						+ social.getActor().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile social) {
		ModelAndView result;

		result = this.createEditModelAndView(social, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile social,
			final String messageCode) {
		final ModelAndView result = new ModelAndView("socialprofile/edit");
		Assert.notNull(social);
		result.addObject("socialProfile", social);
		result.addObject("message", messageCode);
		result.addObject("actor", social.getActor());
		return result;
	}

}
