package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.TutorialService;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Tutorial;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private PresentationService presentationService;
	
	@Autowired
	private ConferenceService conferenceService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int conferenceId) {
		ModelAndView res;
		Collection<Tutorial> tutorials;
		Collection<Panel> panels;
		Collection<Presentation> presentations;
		Conference conference;
		boolean conferencePast = false;
		
		conference = this.conferenceService.findOne(conferenceId);
		
		if(this.conferenceService.findPastConferences().contains(conference)){
			conferencePast = true;
		}		

		tutorials = this.tutorialService.findTutorialsByConferenceId(conferenceId);
		panels = this.panelService.findPanelsByConferenceId(conferenceId);
		presentations = this.presentationService.findPresentationsByConferenceId(conferenceId);

		res = new ModelAndView("activity/list");
		res.addObject("tutorials", tutorials);
		res.addObject("panels", panels);
		res.addObject("presentations", presentations);
		res.addObject("conferencePast", conferencePast);

		return res;
	}
}
