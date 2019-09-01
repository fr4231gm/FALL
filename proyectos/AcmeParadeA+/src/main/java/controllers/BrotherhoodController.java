/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import services.ChapterService;
import services.HistoryService;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.History;
import forms.BrotherhoodForm;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	// Singletons
	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private AreaService			areaService;

	@Autowired
	private ChapterService		chapterService;


	// Constructors -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", "brotherhood/list.do");

		return result;
	}

	// Display A Brotherhood ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"brotherhoodId"
	})
	public ModelAndView display(final int brotherhoodId) {
		ModelAndView result;

		//Initialize variables
		Brotherhood brotherhood;
		String pictures;
		History history;
		history = this.historyService.findByBrotherhoodId(brotherhoodId);
		brotherhood = this.brotherhoodService.construct(this.brotherhoodService.findOne(brotherhoodId));

		pictures = brotherhood.getPictures();
		pictures.replace(" ", "");
		final String[] aux = pictures.split("\r\n");

		result = new ModelAndView("brotherhood/display");
		result.addObject("brotherhood", brotherhood);
		result.addObject("picturesList", aux);
		result.addObject("withHistory", false);
		if (history != null) {
			result.addObject("historyId", history.getId());
			result.addObject("withHistory", true);
		}

		return result;
	}

	// Create A New Brotherhood
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		BrotherhoodForm brotherhoodForm;
		Collection<Area> areas;

		areas = this.areaService.findAll();

		brotherhoodForm = new BrotherhoodForm();
		brotherhoodForm.setCheckTerms(false);

		result = new ModelAndView("brotherhood/register");
		result.addObject("brotherhoodForm", brotherhoodForm);
		result.addObject("areas", areas);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		//Initialize Variables
		ModelAndView result;
		Brotherhood brotherhood;
		Collection<Area> areas;

		//Create the brotherhood object from the brotherhoodForm
		brotherhood = this.brotherhoodService.reconstruct(brotherhoodForm, binding);

		//If the form has errors prints it
		if (binding.hasErrors()) {
			result = new ModelAndView("brotherhood/register");
			areas = this.areaService.findAll();
			result.addObject("areas", areas);
		} else
			//If the form does not have errors, try to save it
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
				result.addObject("name", brotherhood.getName());
			} catch (final Throwable oops) {
				result = new ModelAndView("brotherhood/register");
				areas = this.areaService.findAll();
				result.addObject("brotherhoodForm", brotherhoodForm);
				result.addObject("areas", areas);
				result.addObject("message", "actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	ModelAndView edit() {
		ModelAndView result;
		Brotherhood brotherhood;
		Collection<Area> areas;

		areas = this.areaService.findAll();
		result = new ModelAndView("brotherhood/edit");

		try {
			brotherhood = this.brotherhoodService.findOneTrimmedByPrincipal();
			result.addObject("brotherhood", brotherhood);
			result.addObject("areas", areas);
		} catch (final Throwable oops) {
			result.addObject("message", "actor.commit.error");
			result.addObject("areas", areas);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result;
		Brotherhood toSave;
		SimpleDateFormat formatter;
		Collection<Area> areas;

		areas = this.areaService.findAll();
		String moment;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		toSave = this.brotherhoodService.reconstruct(brotherhood, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("brotherhood/edit");
			result.addObject("areas", areas);
		} else
			try {
				this.brotherhoodService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("moment", moment);
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = new ModelAndView("brotherhood/edit");
				result.addObject("message", "actor.commit.error");
				result.addObject("areas", areas);
			}

		return result;
	}

	// List by area ---------------------------------------------------------------  

	@RequestMapping(value = "/listByChapter", method = RequestMethod.GET)
	public ModelAndView listByChapter(@RequestParam final int chapterId) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		Area area;
		Chapter chapter;

		chapter = this.chapterService.findOne(chapterId);
		area = this.areaService.findAreaByChapterId(chapter.getId());
		if (area == null) {
			result = new ModelAndView("security/notfind");
			result.addObject("message", "notfind.brotherhoods");
		} else {
			brotherhoods = this.brotherhoodService.findBrotherhoodsByArea(area.getId());

			result = new ModelAndView("brotherhood/list");
			result.addObject("brotherhoods", brotherhoods);
			result.addObject("requestURI", "brotherhood/listByChapter.do?chapterId=" + chapter.getId());
		}

		return result;
	}

}
