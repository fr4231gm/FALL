package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.ConferenceService;
import services.TutorialService;
import services.UtilityService;
import domain.Conference;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private UtilityService utilityService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView res;
		Tutorial tutorial;

		tutorial = this.tutorialService.create(conferenceId);
		Assert.notNull(tutorial);
		res = this.createEditModelAndView(tutorial);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tutorialId) {
		ModelAndView res;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		res = this.createEditModelAndView(tutorial);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid Tutorial tutorial, BindingResult binding) {
		ModelAndView res;
		boolean conferencePast = false;

		if (this.conferenceService.findPastConferences().contains(
				tutorial.getConference())) {
			conferencePast = true;
		}

		if (this.utilityService.checkUrls(tutorial.getAttachments())) {
			binding.rejectValue("attachments", "activity.attachments.error");
		}

		if (!this.activityService.checkStartMoment(tutorial)) {
			binding.rejectValue("startMoment", "activity.startMoment.error");
		}

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(tutorial);
		} else {
			try {
				this.tutorialService.save(tutorial);
				res = new ModelAndView(
						"redirect:/tutorial/display.do?tutorialId="
								+ tutorial.getId());
				res.addObject("tutorial", tutorial);
				res.addObject("schedule",
						this.activityService.getSchedule(tutorial));
				res.addObject("conferencePast", conferencePast);

			} catch (Throwable oops) {
				res = this.createEditModelAndView(tutorial,
						"activity.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tutorial tutorial, BindingResult binding) {
		ModelAndView res;
		boolean conferencePast = false;

		if (this.conferenceService.findPastConferences().contains(
				tutorial.getConference())) {
			conferencePast = true;
		}

		if (this.utilityService.checkUrls(tutorial.getAttachments())) {
			binding.rejectValue("attachments", "activity.attachments.error");
		}
		
		if (!this.activityService.checkStartMoment(tutorial)) {
			binding.rejectValue("startMoment", "activity.startMoment.error");
		}

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(tutorial);
		} else {
			try {
				this.tutorialService.save(tutorial);
				res = new ModelAndView(
						"redirect:/tutorial/display.do?tutorialId="
								+ tutorial.getId());
				res.addObject("tutorial", tutorial);
				res.addObject("schedule",
						this.activityService.getSchedule(tutorial));
				res.addObject("conferencePast", conferencePast);

			} catch (Throwable oops) {
				res = this.createEditModelAndView(tutorial,
						"activity.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tutorialId) {
		ModelAndView res;
		Tutorial tutorial;
		boolean conferencePast = false;

		tutorial = this.tutorialService.findOne(tutorialId);

		if (this.conferenceService.findPastConferences().contains(
				tutorial.getConference())) {
			conferencePast = true;
		}

		res = new ModelAndView("tutorial/display");
		res.addObject("tutorial", tutorial);
		res.addObject("schedule", this.activityService.getSchedule(tutorial));
		res.addObject("conferencePast", conferencePast);

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int tutorialId) {

		ModelAndView result;
		Tutorial tutorial;
		Conference conference;

		tutorial = this.tutorialService.findOne(tutorialId);
		conference = tutorial.getConference();

		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView(
					"redirect:/activity/list.do?conferenceId="
							+ conference.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tutorial,
					"activity.commit.error");
		}

		return result;

	}

	// Ancilliary methods

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		final ModelAndView result = this.createEditModelAndView(tutorial, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("tutorial/edit");

		result.addObject("tutorial", tutorial);
		result.addObject("message", messagecode);

		return result;
	}
}
