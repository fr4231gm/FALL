package controllers.author;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConferenceService;
import services.RegistrationService;

import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/conference/author")
public class ConferenceAuthorController extends AbstractController {

	// Services
	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private AuthorService authorService;


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

		return res;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET, params = "conferenceId")
	public ModelAndView register(@RequestParam final int conferenceId) {
		ModelAndView res;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		
		try {
			
			Registration registration = this.registrationService.create(conferenceId);
			registration.setConference(conference);
			this.registrationService.save(registration);
			
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		res = new ModelAndView("redirect:/conference/author/list.do");

		return res;
	}
	
	//Show
		@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
			"conferenceId"
		})
		public ModelAndView displayAnonymous(@RequestParam final int conferenceId) {
			ModelAndView res;
			Boolean future = false;
			Boolean haveR = true;
			Author principal = authorService.findByPrincipal();
			Collection<Registration> registerConference = registrationService.findRegistrationsByConferenceId(conferenceId);

			// Initialize variables
			Conference c;
			c = this.conferenceService.findOne(conferenceId);
			
			if(conferenceService.findForthcomingConferences().contains(c)){
				future = true;
			}
			
			for(Registration r: registerConference){
				if(r.getAuthor().equals(principal)){
					haveR = true;
				}
			}	

			res = new ModelAndView("conference/display");
			res.addObject("conference", c);
			res.addObject("future", future);
			res.addObject("haveR", haveR);


			return res;
		}

}
