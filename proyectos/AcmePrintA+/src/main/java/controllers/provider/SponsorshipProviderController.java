 
package controllers.provider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PostService;
import services.ProviderService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship/provider")
public class SponsorshipProviderController extends AbstractController {

	//Constructors--------------------------------------------------------
	public SponsorshipProviderController() {
		super();
	}


	//Service-------------------------------------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private PostService			postService;


	//List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByProviderId();
		res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsorship/provider/list.do");
		return res;
	}

	//Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		final Sponsorship sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
		final Provider principal = this.providerService.findByPrincipal();

		if (sponsorship == null)
			res = new ModelAndView("security/notfind");
		else if (sponsorship.getProvider().getId() != principal.getId())
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

	//Create-------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setId(0);
		sponsorshipForm.setIsEnabled(true);
		sponsorshipForm.setCost(0.0);
		res = new ModelAndView("sponsorship/create");
		res.addObject("sponsorshipForm", sponsorshipForm);
		res.addObject("posts", this.postService.findPostsFinalMode());

		return res;
	}

	// Edit ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView res;

		Sponsorship sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
		final Provider principal = this.providerService.findByPrincipal();
		if (sponsorshipId != 0 && sponsorship == null)
			res = new ModelAndView("security/notfind");
		else if (sponsorship.getProvider().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				Assert.isTrue(this.sponsorshipService.findOne(sponsorshipId).getProvider().getId() == principal.getId());
				try {
					sponsorship = this.sponsorshipService.findOne(sponsorshipId);
					Assert.notNull(sponsorship);
					final SponsorshipForm sponsorshipForm = this.sponsorshipService.construct(sponsorship);
					res = this.createEditModelAndView(sponsorshipForm);
				} catch (final Throwable oops) {
					res = new ModelAndView();
				}
			} catch (final Throwable oops) {
				res = new ModelAndView("security/hacking");
			}
		return res;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save( final SponsorshipForm sponsorshipForm, final BindingResult binding) {
		ModelAndView res;
		Sponsorship sponsorship;
		Sponsorship sponsorshipSaved;
		if (sponsorshipForm.getId() != 0 && this.sponsorshipService.findOneToFail(sponsorshipForm.getId()) == null)
			res = new ModelAndView("security/notfind");
		else {
			sponsorship = this.sponsorshipService.reconstruct(sponsorshipForm, binding);
			if (binding.hasErrors()) {
				if (sponsorship.getId() == 0) {
					res = this.createEditModelAndView2(sponsorshipForm, null);
					res.addObject("posts", this.postService.findPostsFinalMode());
				} else
					res = this.createEditModelAndView(sponsorshipForm, null);
			} else
				try {
					sponsorshipSaved = this.sponsorshipService.save(sponsorship);
					Assert.notNull(sponsorshipSaved);
					res = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					if (sponsorship.getId() == 0) {
						res = this.createEditModelAndView2(sponsorshipForm, "sponsorship.commit.error.save");
						res.addObject("posts", this.postService.findPostsFinalMode());
					} else
						res = this.createEditModelAndView(sponsorshipForm, "sponsorship.commit.error.save");
				}
		}
		return res;
	}

	// Desactivate -------------------------------------------------------------------------------------
	@RequestMapping(value = "/desactivate", method = RequestMethod.GET)
	public ModelAndView desactivate(@RequestParam final Integer sponsorshipId) {
		ModelAndView res;
		final Provider principal = this.providerService.findByPrincipal();
		final Sponsorship sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
		if (sponsorship.getProvider().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				this.sponsorshipService.desactivate(sponsorshipId);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {

				Collection<Sponsorship> sponsorships;
				sponsorships = this.sponsorshipService.findSponsorshipsByProviderId();

				res = new ModelAndView("sponsorship/list");
				res.addObject("message", "sponsorship.commit.error.delete");
				res.addObject("sponsorships", sponsorships);
				res.addObject("requestURI", "/sponsorship/list.do");

			}

		return res;
	}

	// Activate -------------------------------------------------------------------------------------
		@RequestMapping(value = "/activate", method = RequestMethod.GET)
		public ModelAndView activate(@RequestParam final Integer sponsorshipId) {
			ModelAndView res;
			final Provider principal = this.providerService.findByPrincipal();
			final Sponsorship sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
			if (sponsorship.getProvider().getId() != principal.getId())
				res = new ModelAndView("security/hacking");
			else
				try {
					this.sponsorshipService.activate(sponsorshipId);
					res = new ModelAndView("redirect:list.do");

				} catch (final Throwable oops) {

					Collection<Sponsorship> sponsorships;
					sponsorships = this.sponsorshipService.findSponsorshipsByProviderId();

					res = new ModelAndView("sponsorship/list");
					res.addObject("message", "sponsorship.commit.error.delete");
					res.addObject("sponsorships", sponsorships);
					res.addObject("requestURI", "/sponsorship/list.do");

				}

			return res;
		}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm) {
		ModelAndView res;
		res = this.createEditModelAndView(sponsorshipForm, null);
		return res;
	}
	//edit
	protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("sponsorship/edit");
		res.addObject("sponsorshipForm", sponsorshipForm);
		res.addObject("message", messageCode);

		return res;
	}
	//create
	protected ModelAndView createEditModelAndView2(final SponsorshipForm sponsorshipForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("sponsorship/create");
		res.addObject("sponsorshipForm", sponsorshipForm);
		res.addObject("message", messageCode);

		return res;
	}

}
