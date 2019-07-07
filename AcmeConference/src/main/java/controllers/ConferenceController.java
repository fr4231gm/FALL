
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/listRunningConferences", method = RequestMethod.GET)
	public ModelAndView listRunningConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findRunningConferences();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listRunningConferences.do");

		return result;
	}

	//Show
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"conferenceId"
	})
	public ModelAndView displayAnonymous(@RequestParam final int conferenceId) {
		ModelAndView res;

		// Initialize variables
		Conference c;
		c = this.conferenceService.findOne(conferenceId);

		res = new ModelAndView("conference/display");
		res.addObject("conference", c);

		return res;
	}

}
