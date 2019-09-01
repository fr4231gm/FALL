/*
 * ApplicationRookieController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.rookie;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CurriculaService;
import services.RookieService;
import controllers.AbstractController;
import domain.Application;
import domain.Curricula;
import domain.Rookie;
import domain.Problem;
import forms.ApplicationForm;

@Controller
@RequestMapping("/application/rookie")
public class ApplicationRookieController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private CurriculaService	curriculaService;


	// Constructors -----------------------------------------------------------
	public ApplicationRookieController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Application> applications;
		Rookie principal;

		principal = this.rookieService.findByPrincipal();

		applications = this.applicationService.findApplicationsByRookie(principal.getId());

		res = new ModelAndView("application/list");
		res.addObject("applications", applications);
		res.addObject("requestURI", "/application/rookie/list.do");
		res.addObject("permiso", true);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView res;
		Application application;
		ApplicationForm applicationForm;
		Collection<Curricula> curriculas;
		Problem problem;

		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		application = this.applicationService.create(positionId);
		applicationForm = this.applicationService.construct(application);
		problem = application.getProblem();

		res = new ModelAndView("application/edit");
		if (curriculas.isEmpty()) {
			res.addObject("crearCurricula", "curricula.create.error");
			res.addObject("guardar", false);
		} else
			res.addObject("guardar", true);
		res.addObject("applicationForm", applicationForm);
		res.addObject("curriculas", curriculas);
		res.addObject("problem", problem);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView res;
		Application application;
		Collection<Curricula> curriculas;

		application = this.applicationService.reconstruct(applicationForm, binding);
		final Problem problem = application.getProblem();
		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(application);
			res.addObject("curriculas", curriculas);
			res.addObject("guardar", true);
			res.addObject("problem", problem);

		} else
			try {
				this.applicationService.save(application);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(application, "application.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView res;
		ApplicationForm applicationForm;
		Rookie principal;
		boolean permiso = false;
		Collection<Curricula> curriculas;

		principal = this.rookieService.findByPrincipal();
		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		try {
			final Application application = this.applicationService.findOne(applicationId);
			final Problem problem = application.getProblem();
			if (application.getRookie() == principal)
				permiso = true;
			Assert.isTrue(application.getRookie() == principal && application.getStatus().equals("PENDING"));

			try {
				applicationForm = this.applicationService.construct(this.applicationService.findOne(applicationId));
				res = this.createEditModelAndViewForm(applicationForm);
				res.addObject("permiso", permiso);
				res.addObject("guardar", true);
				res.addObject("curriculas", curriculas);
				res.addObject("problem", problem);
			} catch (final Throwable oops) {
				res = new ModelAndView();
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}

	// Save to edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView res;
		Application application;
		Collection<Curricula> curriculas;

		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		application = this.applicationService.reconstruct(applicationForm, binding);
		final Problem problem = application.getProblem();
		Assert.isTrue(application.getStatus().equals("PENDING"));

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(application);
				res.addObject("curriculas", curriculas);
				res.addObject("guardar", true);
				res.addObject("problem", problem);

			} else
				try {
					this.applicationService.save(application);
					res = new ModelAndView("redirect:list.do");

				} catch (final Throwable oops) {

					res = this.createEditModelAndViewForm(applicationForm, "application.commit.error");
					res.addObject("curriculas", curriculas);
				}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}

	// Submit
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public ModelAndView submit(@RequestParam final int applicationId) {
		ModelAndView res;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.isTrue(application.getStatus().equals("PENDING"));

		try {
			try {
				this.applicationService.submit(application);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:list.do");
				res.addObject("message", "application.submit.error");
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;

		//Initialize variables
		Application application;
		Rookie principal;
		Curricula curricula;

		principal = this.rookieService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);
		curricula = application.getCurricula();

		if (principal.getId() != application.getRookie().getId())
			result = new ModelAndView("security/hacking");
		else {
			result = new ModelAndView("application/display");
			result.addObject("application", application);
			result.addObject("permiso", true);
			result.addObject("curricula", curricula);
		}

		return result;
	}
	// Ancillary metods

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;
		result = this.createEditModelAndView(application, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndViewForm(final ApplicationForm applicationForm) {
		ModelAndView result;
		result = this.createEditModelAndViewForm(applicationForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForm(final ApplicationForm applicationForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("applicationForm", applicationForm);
		result.addObject("message", messageCode);

		return result;
	}
}
