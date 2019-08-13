
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/listDeadlineElapsed", method = RequestMethod.GET)
	public ModelAndView listDeadlineElapsed() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findDeadlineElapsed();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listDeadlineElapsed.do");

		return result;
	}

	@RequestMapping(value = "/listNotificationElapsed", method = RequestMethod.GET)
	public ModelAndView listNotificationElapsed() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findNotificationElapsed();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listNotificationElapsed.do");

		return result;
	}

	@RequestMapping(value = "/listCameraElapsed", method = RequestMethod.GET)
	public ModelAndView listCameraElapsed() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findCameraReadyElapsed();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listCameraElapsed.do");

		return result;
	}

	@RequestMapping(value = "/listFutureConferences", method = RequestMethod.GET)
	public ModelAndView listFutureConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findFutureConferences();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listFutureConferences.do");

		return result;
	}

}
