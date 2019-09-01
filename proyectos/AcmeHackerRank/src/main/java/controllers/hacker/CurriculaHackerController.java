/*
 * LegalTermsController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.hacker;

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

import services.CurriculaService;
import services.HackerService;
import services.PersonalDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculaHackerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private HackerService		hackerService;


	// Constructors -----------------------------------------------------------
	public CurriculaHackerController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculaId) {
		ModelAndView result;
		Curricula curricula;
		final Hacker principal;

		try {
			principal = this.hackerService.findByPrincipal();
			curricula = this.curriculaService.findOne(curriculaId);
			Assert.isTrue(curricula.getHacker().equals(principal));
			Assert.notNull(curricula);
			result = new ModelAndView("curricula/display");
			result.addObject("curricula", curricula);
			result.addObject("principal", principal);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect: /list.do");
		}
		return result;

	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		PersonalData personalData;

		personalData = this.personalDataService.create();
		result = this.createEditModelAndView(personalData);
		return result;
	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculaId) {
		ModelAndView result;
		Curricula curricula;
		final Hacker principal = this.hackerService.findByPrincipal();
		try {
			curricula = this.curriculaService.findOneToEdit(curriculaId);
			Assert.isTrue(curricula.getHacker().equals(principal));
			Assert.notNull(curricula);
			result = this.createEditModelAndView(curricula.getPersonalData());
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Curricula> curricula;
		try {
			curricula = this.curriculaService.findNonCopiesByPrincipal();
			Assert.notNull(curricula);
			Hacker principal;
			principal = this.hackerService.findByPrincipal();
			result = new ModelAndView("curricula/list");
			result.addObject("principal", principal);
			result.addObject("curriculas", curricula);
			result.addObject("requestURI", "curricula/hacker/list");
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final PersonalData personalData, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(personalData);
		else
			try {
				this.personalDataService.save(personalData);
				result = new ModelAndView("redirect: /list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalData, "curricula.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PersonalData personalData, final BindingResult binding) {
		ModelAndView result;

		try {
			this.curriculaService.delete(this.curriculaService.findByPersonalDataId(personalData.getId()));
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalData, "curricula.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final PersonalData personalData) {
		ModelAndView result;

		result = this.createEditModelAndView(personalData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalData personalData, final String messageCode) {
		final ModelAndView result;
		Hacker principal;

		principal = this.hackerService.findByPrincipal();

		result = new ModelAndView("curricula/edit");
		result.addObject("personalData", personalData);
		result.addObject("principal", principal);

		result.addObject("message", messageCode);

		return result;

	}

}
