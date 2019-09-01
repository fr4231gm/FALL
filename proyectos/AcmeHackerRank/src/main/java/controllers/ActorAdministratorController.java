
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import domain.Actor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController {

	// Constructors -----------------------------------------------------------

	public ActorAdministratorController() {
		super();
	}


	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configService;


	//List actors
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Actor> actors;
		actors = this.actorService.findAll();
		result = new ModelAndView("actor/listActor");

		result.addObject("actor", actors);
		result.addObject("requestURI", "actor/administrator/list.do");
		result.addObject("configuration", this.configService.findConfiguration());
		return result;
	}

	//Banear actores
	@RequestMapping(value = "/banActor", method = RequestMethod.GET, params = "actorId")
	public ModelAndView banBrotherhood(@RequestParam final int actorId) {

		final ModelAndView result;
		final Actor a;
		a = this.actorService.findOne(actorId);
		this.administratorService.changeBan(a.getUserAccount());

		result = new ModelAndView("redirect:list.do");

		return result;
	}

}
