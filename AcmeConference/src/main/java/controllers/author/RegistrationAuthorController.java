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
		final Registration registration = this.registrationService.create(conferenceId);

		res = this.createEditModelAndView(registration);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Registration reg,
			final BindingResult binding) {
		ModelAndView res;
		Registration aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(reg);
		else
			try {

				aux = this.registrationService.save(reg);
				res = new ModelAndView("redirect:/conference/author/list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(reg, "registration.commit.error");
			}
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
