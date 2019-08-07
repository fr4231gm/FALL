package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PanelService;
import services.PresentationService;
import services.TutorialService;
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int conferenceId) {
		ModelAndView res;
		Collection<Tutorial> tutorials;
		Collection<Panel> panels;
		Collection<Presentation> presentations;

		tutorials = this.tutorialService.findTutorialsByConferenceId(conferenceId);
		panels = this.panelService.findPanelsByConferenceId(conferenceId);
		presentations = this.presentationService.findPresentationsByConferenceId(conferenceId);

		res = new ModelAndView("activity/list");
		res.addObject("tutorials", tutorials);
		res.addObject("panels", panels);
		res.addObject("presentations", presentations);

		return res;
	}
}
