package controllers.author;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConferenceService;

import controllers.AbstractController;
import domain.Conference;

@Controller
@RequestMapping("/conference/author")
public class ConferenceAuthorController extends AbstractController {

	// Services
	@Autowired
	private ConferenceService conferenceService;


	// Constructors -----------------------------------------------------------
	public ConferenceAuthorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Conference> conferences;

		conferences = this.conferenceService
				.findForthcomingConferences();

		res = new ModelAndView("conference/list");
		res.addObject("conferences", conferences);
		res.addObject("requestURI", "/conference/author/list.do");
		// res.addObject("permiso", true);

		return res;
	}

}
