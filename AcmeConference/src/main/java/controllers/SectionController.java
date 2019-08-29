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

import services.ConferenceService;
import services.SectionService;
import services.TutorialService;
import services.UtilityService;
import domain.Section;
import domain.Tutorial;
import forms.SectionForm;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private ConferenceService conferenceService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tutorialId) {
		ModelAndView res;
		Section section;
		
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(
				tutorial.getConference()));

		section = this.sectionService.create(tutorialId);
		SectionForm sectionForm = this.sectionService.construct(section);
		sectionForm.setTutorial(this.tutorialService.findOne(tutorialId));
		res = this.createEditModelAndView(sectionForm);
		res.addObject("requestURI", "section/create.do");

		return res;
	}

	// Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SectionForm sectionForm,
			final BindingResult binding) {
		ModelAndView res;
		Section toSave;

		if (this.utilityService.checkUrls(sectionForm.getPictures())) {
			binding.rejectValue("pictures", "section.pictures.error");
		}

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(sectionForm);
		} else {
			try {
				toSave = this.sectionService.reconstruct(sectionForm);
				toSave = this.sectionService.save(toSave, sectionForm
						.getTutorial().getId());
				Tutorial tutorial = this.tutorialService
						.findTutorialBySectionId(toSave.getId());
				res = new ModelAndView(
						"redirect:/tutorial/display.do?tutorialId="
								+ tutorial.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(sectionForm,
						"section.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int sectionId) {
		ModelAndView res;
		Section section;

		section = this.sectionService.findOne(sectionId);
		SectionForm sectionForm = this.sectionService.construct(section);
		Assert.notNull(section);

		res = this.createEditModelAndView(sectionForm);
		res.addObject("requestURI", "section/edit.do");

		return res;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final SectionForm sectionForm,
			final BindingResult binding) {
		ModelAndView res;
		Section toSave;

		if (this.utilityService.checkUrls(sectionForm.getPictures())) {
			binding.rejectValue("pictures", "section.pictures.error");
		}

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(sectionForm);
		} else {
			try {
				toSave = this.sectionService.reconstruct(sectionForm);
				toSave = this.sectionService.save(toSave, sectionForm
						.getTutorial().getId());
				Tutorial tutorial = this.tutorialService
						.findTutorialBySectionId(toSave.getId());
				res = new ModelAndView(
						"redirect:/tutorial/display.do?tutorialId="
								+ tutorial.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(sectionForm,
						"section.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sectionId) {

		ModelAndView result;
		Section section;
		Tutorial tutorial;

		section = this.sectionService.findOne(sectionId);
		tutorial = this.tutorialService.findTutorialBySectionId(sectionId);
		
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(
				tutorial.getConference()));

		try {
			this.sectionService.delete(section);
			result = new ModelAndView(
					"redirect:/tutorial/display.do?tutorialId="
							+ tutorial.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView(
					"redirect:/tutorial/display.do?tutorialId="
							+ tutorial.getId());
			result.addObject("tutorial", tutorial);
			result.addObject("message", "activity.commit.error");
		}

		return result;

	}

	// Ancilliary methods

	protected ModelAndView createEditModelAndView(final SectionForm sectionForm) {
		final ModelAndView result = this.createEditModelAndView(sectionForm,
				null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final SectionForm sectionForm, final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("section/edit");

		result.addObject("sectionForm", sectionForm);
		result.addObject("message", messagecode);

		return result;
	}
}
