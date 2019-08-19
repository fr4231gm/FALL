package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConferenceService;
import services.RegistrationService;
import domain.Author;
import domain.Comment;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private AuthorService authorService;

	@RequestMapping(value = "/listRunningConferences", method = RequestMethod.GET)
	public ModelAndView listRunningConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findRunningConferences();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");
		final Date actual = new Date(System.currentTimeMillis() - 1);

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listRunningConferences.do");
		result.addObject("general", true);
		result.addObject("searchPoint", "conference/listSearchRunning.do");
		result.addObject("fechaActual", actual);

		return result;
	}

	@RequestMapping(value = "/listForthcomingConferences", method = RequestMethod.GET)
	public ModelAndView listForthcomingConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findForthcomingConferences();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");
		final Date actual = new Date(System.currentTimeMillis() - 1);

		result.addObject("conferences", conferences);
		result.addObject("requestURI",
				"conference/listForthcomingConferences.do");
		result.addObject("general", true);
		result.addObject("searchPoint", "conference/listSearchForthcoming.do");
		result.addObject("fechaActual", actual);

		return result;
	}

	@RequestMapping(value = "/listPastConferences", method = RequestMethod.GET)
	public ModelAndView listPastConferences() {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findPastConferences();
		Assert.notNull(conferences);

		result = new ModelAndView("conference/list");
		final Date actual = new Date(System.currentTimeMillis() - 1);

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listPastConferences.do");
		result.addObject("general", true);
		result.addObject("searchPoint", "conference/listSearchPast.do");
		result.addObject("fechaActual", actual);

		return result;
	}

	// Show
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "conferenceId" })
	public ModelAndView displayAnonymous(@RequestParam final int conferenceId) {
		ModelAndView res;
		Boolean future = false;
		Boolean canCreateActivity = false;
		Boolean haveR = false;
		Collection<Registration> registerConference = registrationService
				.findRegistrationsByConferenceId(conferenceId);
		Collection<Comment> comments;

		// Initialize variables
		Conference c;
		c = this.conferenceService.findOne(conferenceId);

		if (conferenceService.findForthcomingConferences().contains(c)) {
			future = true;
			canCreateActivity = true;
		}

		if (conferenceService.findRunningConferences().contains(c)) {
			canCreateActivity = true;
		}
		
		comments = c.getComments();

		try {

			Author principal = authorService.findByPrincipal();

			for (Registration r : registerConference) {
				if (r.getAuthor().equals(principal)) {
					haveR = true;
				}
			}

			res = new ModelAndView("conference/display");
			res.addObject("conference", c);
			res.addObject("future", future);
			res.addObject("haveR", haveR);
			res.addObject("canCreateActivity", canCreateActivity);
			res.addObject("comments", comments);

			if (haveR == true) {
				res.addObject("message", "registration.commit.error");
			}
		} catch (final Throwable oops) {

			res = new ModelAndView("conference/display");
			res.addObject("conference", c);
			res.addObject("future", future);
			res.addObject("haveR", haveR);
			res.addObject("canCreateActivity", canCreateActivity);
			res.addObject("comments", comments);
		}

		return res;
	}

	@RequestMapping(value = "/listSearchPast", method = RequestMethod.GET, params = { "keyword" })
	public ModelAndView listSearchPast(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService
				.searchConferenceAnonymousPast(keyword);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listSearchPast.do");
		result.addObject("general", true);
		result.addObject("searchPoint", "conference/listSearchPast.do");
		final Date actual = new Date(System.currentTimeMillis() - 1);
		result.addObject("fechaActual", actual);

		return result;
	}

	@RequestMapping(value = "/listSearchForthcoming", method = RequestMethod.GET, params = { "keyword" })
	public ModelAndView listSearchForthcoming(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService
				.searchConferenceAnonymousForthcomming(keyword);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listSearchForthcoming.do");
		result.addObject("general", true);
		result.addObject("searchPoint", "conference/listSearchForthcoming.do");
		final Date actual = new Date(System.currentTimeMillis() - 1);
		result.addObject("fechaActual", actual);

		return result;
	}

	@RequestMapping(value = "/listSearchRunning", method = RequestMethod.GET, params = { "keyword" })
	public ModelAndView listSearchRunning(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService
				.searchConferenceAnonymousRunning(keyword);

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/listSearchRunning.do");
		result.addObject("general", true);
		result.addObject("searchPoint", "conference/listSearchRunning.do");
		final Date actual = new Date(System.currentTimeMillis() - 1);
		result.addObject("fechaActual", actual);

		return result;
	}

}
