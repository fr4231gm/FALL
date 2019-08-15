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

import services.SectionService;
import services.TutorialService;
import services.UtilityService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private UtilityService utilityService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tutorialId) {
		ModelAndView res;
		Section section;

		section = this.sectionService.create(tutorialId);
		res = this.createEditModelAndView(section);
		res.addObject("requestURI", "section/create.do");

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int sectionId) {
		ModelAndView res;
		Section section;

		section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);

		res = this.createEditModelAndView(section);
		res.addObject("requestURI", "section/edit.do");

		return res;
	}

	// Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section,
			final BindingResult binding) {
		ModelAndView res;
		Section toSave;
		
		if(this.utilityService.checkUrls(section.getPictures())){
			binding.rejectValue("pictures", "section.pictures.error");
		}
		
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(section);
		} else {
			try {
				binding.rejectValue("pictures", "section.pictures.error");
				toSave = this.sectionService.save(section);
				Tutorial tutorial = this.tutorialService
						.findTutorialBySectionId(toSave.getId());
				res = new ModelAndView(
						"redirect:/tutorial/display.do?tutorialId="
								+ tutorial.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(section,
						"section.commit.error");
			}
		}

		return res;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Section section,
			final BindingResult binding) {
		ModelAndView res;
		Section toSave;
		
		if(this.utilityService.checkUrls(section.getPictures())){
			binding.rejectValue("pictures", "section.pictures.error");
		}
		
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(section);
		} else {
			try {
				
				toSave = this.sectionService.save(section);
				Tutorial tutorial = this.tutorialService
						.findTutorialBySectionId(toSave.getId());
				res = new ModelAndView(
						"redirect:/tutorial/display.do?tutorialId="
								+ tutorial.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(section,
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

		try {
			this.sectionService.delete(section);
			result = new ModelAndView("redirect:/tutorial/display.do?tutorialId="+tutorial.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/tutorial/display.do?tutorialId="+tutorial.getId());
			result.addObject("tutorial", tutorial);
			result.addObject("message", "activity.commit.error");
		}

		return result;

	}

	// Ancilliary methods

	protected ModelAndView createEditModelAndView(final Section section) {
		final ModelAndView result = this.createEditModelAndView(section, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("section/edit");

		result.addObject("section", section);
		result.addObject("message", messagecode);

		return result;
	}
}
