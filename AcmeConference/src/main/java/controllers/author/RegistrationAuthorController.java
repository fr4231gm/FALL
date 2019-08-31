package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConfigurationService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Author;
import domain.Registration;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	// Services
	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructors -----------------------------------------------------------
	public RegistrationAuthorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Registration> registrations;
		Author principal;

		principal = this.authorService.findByPrincipal();

		registrations = this.registrationService
				.findRegistrationsByAuthorId(principal.getId());

		res = new ModelAndView("registration/list");
		res.addObject("registrations", registrations);
		res.addObject("requestURI", "/registration/author/list.do");
		// res.addObject("permiso", true);

		return res;
	}

	// Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView res;
		final Registration registration = this.registrationService
				.create(conferenceId);
		Author principal;
		principal = this.authorService.findByPrincipal();
		String[] makes;

		makes = this.configurationService.findConfiguration().getMake()
				.split(",");

		if (registration.getAuthor().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else{
		
			res = this.createEditModelAndView(registration);
			res.addObject("makes", makes);
		}	
			
		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Registration reg,
			final BindingResult binding) {
		ModelAndView res;
		String[] makes;

		makes = this.configurationService.findConfiguration().getMake()
				.split(",");

		try {

			registrationService.checkCreditCard(reg.getCreditCard(), binding);

			if (binding.hasErrors()) {
				res = this.createEditModelAndView(reg);
				res.addObject("makes", makes);
			} else {
				this.registrationService.save(reg);
				res = new ModelAndView("redirect:/registration/author/list.do");
				res.addObject("makes", makes);
			}

		} catch (final Throwable oops) {
			res = this.createEditModelAndView(reg, "registration.commit.error");
			res.addObject("makes", makes);
		}
		return res;
	}

	// Show
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "registrationId" })
	public ModelAndView display(@RequestParam final int registrationId) {
		ModelAndView res;

		// Initialize variables
		Registration r;
		r = this.registrationService.findOne(registrationId);

		res = new ModelAndView("registration/display");
		res.addObject("registration", r);

		return res;
	}

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final Registration reg) {
		ModelAndView result;
		result = this.createEditModelAndView(reg, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Registration reg,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("registration/register");
		result.addObject("registration", reg);
		result.addObject("message", messageCode);

		return result;
	}

}
