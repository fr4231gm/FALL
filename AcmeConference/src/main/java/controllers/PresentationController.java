package controllers;

import java.util.Collection;

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
import services.PresentationService;
import services.UtilityService;
import domain.Conference;
import domain.Presentation;
import domain.Submission;

@Controller
@RequestMapping("/presentation")
public class PresentationController extends AbstractController {

	@Autowired
	private PresentationService presentationService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private ConferenceService conferenceService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView res;
		Presentation presentation;

		presentation = this.presentationService.create(conferenceId);
		Assert.notNull(presentation);
		res = this.createEditModelAndView(presentation);
		res.addObject("actionURI", "presentation/create.do");

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int presentationId) {
		ModelAndView res;
		Presentation presentation;

		presentation = this.presentationService.findOne(presentationId);
		Assert.notNull(presentation);
		res = this.createEditModelAndView(presentation);
		res.addObject("actionURI", "presentation/edit.do");

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Presentation presentation,
			BindingResult binding) {
		ModelAndView res;
		boolean conferencePast = false;

		if (this.conferenceService.findPastConferences().contains(
				presentation.getConference())) {
			conferencePast = true;
		}

		if (this.utilityService.checkUrls(presentation.getAttachments())) {
			binding.rejectValue("attachments", "activity.attachments.error");
		}

		if (presentation.getStartMoment() != null) {
			if (!this.activityService.checkStartMoment(presentation)) {
				binding.rejectValue("startMoment", "activity.startMoment.error");
			}
		}

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(presentation);
		} else {
			try {
				this.presentationService.save(presentation);
				res = new ModelAndView("presentation/display");
				res.addObject("presentation", presentation);
				res.addObject("schedule",
						this.activityService.getSchedule(presentation));
				res.addObject("conferencePast", conferencePast);
				res.addObject("actionURI", "presentation/create.do");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(presentation,
						"activity.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid Presentation presentation,
			BindingResult binding) {
		ModelAndView res;
		boolean conferencePast = false;

		if (this.conferenceService.findPastConferences().contains(
				presentation.getConference())) {
			conferencePast = true;
		}

		if (this.utilityService.checkUrls(presentation.getAttachments())) {
			binding.rejectValue("attachments", "activity.attachments.error");
		}

		if (presentation.getStartMoment() != null) {
			if (!this.activityService.checkStartMoment(presentation)) {
				binding.rejectValue("startMoment", "activity.startMoment.error");
			}
		}

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(presentation);
			
		} else {
			try {
				this.presentationService.save(presentation);
				res = new ModelAndView("presentation/display");
				res.addObject("presentation", presentation);
				res.addObject("schedule",
						this.activityService.getSchedule(presentation));
				res.addObject("conferencePast", conferencePast);
				res.addObject("actionURI", "presentation/edit.do");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(presentation,
						"activity.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int presentationId) {
		ModelAndView res;
		Presentation presentation;

		presentation = this.presentationService.findOne(presentationId);

		res = new ModelAndView("presentation/display");
		res.addObject("presentation", presentation);
		res.addObject("schedule",
				this.activityService.getSchedule(presentation));

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int presentationId) {

		ModelAndView result;
		Presentation presentation;
		Conference conference;

		presentation = this.presentationService.findOne(presentationId);
		conference = presentation.getConference();

		try {
			this.presentationService.delete(presentation);
			result = new ModelAndView(
					"redirect:/activity/list.do?conferenceId="
							+ conference.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(presentation,
					"activity.commit.error");
		}

		return result;

	}

	// Ancilliary methods

	protected ModelAndView createEditModelAndView(
			final Presentation presentation) {
		final ModelAndView result = this.createEditModelAndView(presentation,
				null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Presentation presentation, final String messagecode) {
		final ModelAndView result;
		Collection<Submission> submissions;

		submissions = this.conferenceService
				.findSubmissionsPapersAcceptedByConferenceId(presentation.getConference()
						.getId());

		result = new ModelAndView("presentation/edit");

		result.addObject("presentation", presentation);
		result.addObject("message", messagecode);
		result.addObject("submissions", submissions);

		return result;
	}
}
