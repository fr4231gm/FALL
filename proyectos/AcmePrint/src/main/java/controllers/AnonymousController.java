package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnonymousService;
import domain.Actor;

@Controller
@RequestMapping("/anonymous")
public class AnonymousController extends AbstractController {

	// Services
	@Autowired
	private AnonymousService anonymousService;

	@Autowired
	private ActorService actorService;

	public AnonymousController() {
		super();
	}

	// Methods.

	@RequestMapping(value = "/out", method = RequestMethod.GET)
	public ModelAndView out() {
		ModelAndView res;
		res = new ModelAndView("anonymous/dropOut");
		return res;
	}

	@RequestMapping(value = "/dropOut", method = RequestMethod.GET)
	public ModelAndView dropOut() {
		// Declaración de variables
		ModelAndView res;
		Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		try {
			Actor logueado = this.actorService.findOne(principal.getId());
			this.anonymousService.changeThings();
			res = new ModelAndView("redirect:/j_spring_security_logout");
			this.anonymousService.dropOut(logueado);

		} catch (final Throwable Oops) {
			res = new ModelAndView();
			res.addObject("message", "anonymous.delete");
		}

		return res;

	}
}
