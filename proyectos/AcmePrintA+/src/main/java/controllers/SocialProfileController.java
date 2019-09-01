
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
import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("/social-profile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	public SocialProfileController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listMySocialProfiles", method = RequestMethod.GET)
	public ModelAndView listMySocialProfiles() {
		ModelAndView result;
		Collection<SocialProfile> social;

		social = this.socialProfileService.findByActor(this.actorService.findByPrincipal().getId());
		Assert.notNull(social);

		result = new ModelAndView("socialprofile/list");

		result.addObject("id", this.actorService.findByPrincipal().getId());
		result.addObject("social", social);
		result.addObject("requestURI", "socialprofile/list.do");
		result.addObject("permiso", true);

		return result;
	}

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
		result.addObject("permiso", false);

		return result;
	}
	// Show

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile social;
		social = this.socialProfileService.findOne(socialProfileId);

		if (socialProfileId != 0 && social == null)
			result = new ModelAndView("security/notfind");
		else {
			result = new ModelAndView("socialprofile/show");
			result.addObject("socialprofile", social);
		}
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		ModelAndView result;
		try {
			Assert.isTrue(this.actorService.findByPrincipal().equals(this.actorService.findOne(actorId)));
			SocialProfile socialProfile;
			socialProfile = this.socialProfileService.create();
			result = this.createEditModelAndView(socialProfile);

		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}

		return result;

	}
	// Edito
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile social;
		try {
			social = this.socialProfileService.findOne(socialProfileId);
			if (!this.socialProfileService.findByActor(this.actorService.findByPrincipal().getId()).contains(social))
				result = new ModelAndView("security/hacking");
			else {

				Assert.notNull(social);
				result = this.createEditModelAndView(social);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;

	}
	// Delete

	@RequestMapping(value = "/remove", method = RequestMethod.GET, params = {
		"id"
	})
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		final SocialProfile sp = this.socialProfileService.findOne(id);

		try {
			if (!this.socialProfileService.findByActor(this.actorService.findByPrincipal().getId()).contains(sp))
				result = new ModelAndView("security/hacking");
			else {
				Assert.isTrue(sp.getActor().getId() == this.actorService.findByPrincipal().getId());
				this.socialProfileService.delete(sp);
				result = new ModelAndView("redirect:listMySocialProfiles.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile social, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(social);
		else
			try {
				social.setActor(this.actorService.findByPrincipal()); //Work around again
				this.socialProfileService.save(social);
				result = new ModelAndView("redirect:listMySocialProfiles.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("socialprofile/edit");
				result.addObject("message", "socialprofile.commit.error");
			}
		return result;
	}

	//Save del create

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final SocialProfile social, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(social);
		else
			try {
				social.setActor(this.actorService.findByPrincipal()); //WorkAround top
				this.socialProfileService.save(social);
				result = new ModelAndView("redirect:listMySocialProfiles.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("socialprofile/edit");
				result.addObject("message", "socialprofile.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile social) {
		ModelAndView result;

		result = this.createEditModelAndView(social, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile social, final String messageCode) {
		final ModelAndView result = new ModelAndView("socialprofile/edit");
		Assert.notNull(social);

		result.addObject("socialProfile", social);
		result.addObject("message", messageCode);
		result.addObject("actor", social.getActor());
		return result;
	}

}
