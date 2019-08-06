package controllers.author;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		//res.addObject("permiso", true);

		return res;
	}

}
