
package controllers.administrator;

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
import services.DesignerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Designer;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

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

	@Autowired
	private DesignerService			designerService;


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

	//Polarity
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public ModelAndView polarity() {
		Collection<Designer> designers;
		Double score;
		ModelAndView result;
		designers = this.designerService.findAll();

		for (final Designer s : designers) {
			score = this.administratorService.score(s.getId());
			if (score != 0 && score > 0)
				s.setScore(score);
			else if (score == 0.0)
				s.setScore(score);

		}
		result = new ModelAndView("redirect:/");
		result.addObject("message", "score.success");
		return result;
	}

	//Display actor profile
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"actorId"
	})
	public ModelAndView display(@RequestParam final int actorId) {
		ModelAndView result;

		// Initialize variables
		Actor actor;
		actor = this.actorService.findOne(actorId);

		//
		Boolean hasSpamMessages = false;
		hasSpamMessages = this.actorService.hasSpamMessages(actor.getId());

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("hasSpamMessages", hasSpamMessages);
		result.addObject("requestURI", "actor/administrator/display.do?actorId=" + actorId);

		return result;
	}

	// Launch Spammer Process
	@RequestMapping(value = "/spammer", method = RequestMethod.GET)
	public ModelAndView spammer() {
		ModelAndView result;

		this.administratorService.flagSpammers();

		result = new ModelAndView("actor/spammer");
		return result;
	}

}
