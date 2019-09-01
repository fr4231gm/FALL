package controllers.sponsor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.ParadeService;
import services.SponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public SponsorshipSponsorController() {
		super();
	}

	// Service ----------------------------------------------------------------
	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private ParadeService paradeService;

	// List --------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Sponsorship> sponsorships;

		// Sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();

		sponsorships = this.sponsorshipService
				.findSponsorshipsBySponsorId(principal.getId());
		res = new ModelAndView("sponsorship/sponsor/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "/sponsorship/sponsor/list.do");
		return res;
	}

	// Create ----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Sponsor principal = this.sponsorService.findByPrincipal();

		final SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setId(0);
		sponsorshipForm.setIsEnabled(true);

		res = new ModelAndView("sponsorship/sponsor/create");
		res.addObject("sponsorshipForm", sponsorshipForm);
		res.addObject("creditCards", this.creditCardService
				.findActiveCreditCardsBySponsorId(principal.getId()));
		res.addObject("parades", this.paradeService.findParadesAccepted());
		return res;
	}

	// Display--------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		Sponsorship sponsorship;
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
		if (sponsorship == null) {
			res = new ModelAndView("security/notfind");
		} else if (sponsorship.getSponsor().getId() != principal.getId()) {
			res = new ModelAndView("security/hacking");
		} else {
			try {

				res = new ModelAndView("sponsorship/sponsor/display");
				res.addObject("sponsorship", sponsorship);

			} catch (final Throwable oops) {
				res = new ModelAndView("security/notfind");
			}
		}
		return res;
	}

	// Edit ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView res;

		Sponsorship sponsorship = this.sponsorshipService
				.findOneToFail(sponsorshipId);
		Sponsor principal = this.sponsorService.findByPrincipal();
		if (sponsorshipId != 0 && sponsorship == null) {
			res = new ModelAndView("security/notfind");
		} else if (sponsorship.getSponsor().getId() != principal.getId()) {
			res = new ModelAndView("security/hacking");
		} else {

			try {
				Assert.isTrue(this.sponsorshipService.findOne(sponsorshipId)
						.getSponsor().getId() == principal.getId());
				try {
					sponsorship = this.sponsorshipService
							.findOne(sponsorshipId);
					Assert.notNull(sponsorship);
					final SponsorshipForm sponsorshipForm = this.sponsorshipService
							.construct(sponsorship);
					res = this.createEditModelAndView(sponsorshipForm);

					res.addObject("creditCards",
							this.creditCardService
									.findActiveCreditCardsBySponsorId(principal
											.getId()));
				} catch (final Throwable oops) {
					res = new ModelAndView();
				}
			} catch (final Throwable oops) {
				res = new ModelAndView("security/hacking");
			}
		}
		return res;
	}

	// Save
	// -------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SponsorshipForm sponsorshipForm,
			final BindingResult binding) {
		ModelAndView res;
		Sponsorship sponsorship;
		Sponsorship sponsorshipSaved;
		Sponsor principal = this.sponsorService.findByPrincipal();
		if (sponsorshipForm.getId() != 0
				&& this.sponsorshipService.findOneToFail(sponsorshipForm
						.getId()) == null) {
			res = new ModelAndView("security/notfind");
		} else {
			sponsorship = this.sponsorshipService.reconstruct(sponsorshipForm,
					binding);
			if (binding.hasErrors()) {
				if (sponsorship.getId() == 0) {
					res = this.createEditModelAndView2(sponsorshipForm,
							"sponsorship.commit.error.save");
					res.addObject("creditCards",
							this.creditCardService
									.findActiveCreditCardsBySponsorId(principal
											.getId()));
					res.addObject("parades",
							this.paradeService.findParadesAccepted());
				} else {
					res = this.createEditModelAndView(sponsorshipForm,
							"sponsorship.commit.error.save");
					res.addObject("creditCards",
							this.creditCardService
									.findActiveCreditCardsBySponsorId(principal
											.getId()));
				}
			} else {
				try {
					Assert.isTrue(sponsorship.getCreditCard().getSponsor()
							.getId() == sponsorship.getSponsor().getId());
					sponsorshipSaved = this.sponsorshipService
							.save(sponsorship);
					Assert.notNull(sponsorshipSaved);
					res = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					if (sponsorship.getId() == 0) {
						res = this.createEditModelAndView2(sponsorshipForm,
								"sponsorship.commit.error.save");
						res.addObject("creditCards", this.creditCardService
								.findActiveCreditCardsBySponsorId(principal
										.getId()));
						res.addObject("parades",
								this.paradeService.findParadesAccepted());
					} else {
						res = this.createEditModelAndView(sponsorshipForm,
								"sponsorship.commit.error.save");
						res.addObject("creditCards", this.creditCardService
								.findActiveCreditCardsBySponsorId(principal
										.getId()));
					}
				}
			}
		}
		return res;
	}

	// Delete
	// -------------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer sponsorshipId) {
		ModelAndView res;
		Sponsor principal = this.sponsorService.findByPrincipal();
		Sponsorship sponsorship = this.sponsorshipService
				.findOneToFail(sponsorshipId);
		if (sponsorship.getSponsor().getId() != principal.getId()) {
			res = new ModelAndView("security/hacking");
		} else {
			try {

				this.sponsorshipService.delete(sponsorshipId);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {

				Collection<Sponsorship> sponsorships;
				sponsorships = this.sponsorshipService
						.findSponsorshipsBySponsorId(principal.getId());

				res = new ModelAndView("sponsorship/sponsor/list.do");
				res.addObject("message", "sponsorship.commit.error.delete");
				res.addObject("sponsorships", sponsorships);
				res.addObject("requestURI", "/sponsorship/sponsor/list.do");

			}
		}

		return res;
	}

	// Activate
	// -------------------------------------------------------------------------------------
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ModelAndView activate(@RequestParam final Integer sponsorshipId) {
		ModelAndView res;
		Sponsorship sponsorship;
		Sponsor principal;
		Collection<Sponsorship> sponsorships;
		sponsorship = this.sponsorshipService.findOneToFail(sponsorshipId);
		principal = this.sponsorService.findByPrincipal();
		try {

			if (sponsorshipId != 0 && sponsorship == null) {
				res = new ModelAndView("security/notfind");
			} else if (sponsorship.getSponsor().getId() != principal.getId()) {
				res = new ModelAndView("security/hacking");
			} else {
				this.sponsorshipService.activate(sponsorshipId);
				res = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			sponsorships = this.sponsorshipService
					.findSponsorshipsBySponsorId(principal.getId());

			res = new ModelAndView("sponsorship/sponsor/list.do");
			res.addObject("message", "sponsorship.commit.error.delete");
			res.addObject("sponsorships", sponsorships);
			res.addObject("requestURI", "/sponsorship/sponsor/list.do");

		}
		return res;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(
			final SponsorshipForm sponsorshipForm) {
		ModelAndView res;
		res = this.createEditModelAndView(sponsorshipForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(
			final SponsorshipForm sponsorshipForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("sponsorship/sponsor/edit");
		res.addObject("sponsorshipForm", sponsorshipForm);
		res.addObject("message", messageCode);

		return res;
	}

	protected ModelAndView createEditModelAndView2(
			final SponsorshipForm sponsorshipForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("sponsorship/sponsor/create");
		res.addObject("sponsorshipForm", sponsorshipForm);
		res.addObject("message", messageCode);

		return res;
	}
}
