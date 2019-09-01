
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Curricula;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;


	// Constructors -----------------------------------------------------------
	public ApplicationCompanyController() {
		super();
	}

	// List the applications of its positions

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Application> applications;
		Company principal;

		principal = this.companyService.findByPrincipal();

		applications = this.applicationService.findApplicationsByCompany(principal.getId());

		res = new ModelAndView("application/list");
		res.addObject("applications", applications);
		res.addObject("requestURI", "/application/company/list.do");
		res.addObject("permiso", true);

		return res;
	}

	// Display an application

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;

		//Initialize variables
		Application application;
		Company principal;
		Curricula curricula;

		principal = this.companyService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);
		curricula = application.getCurricula();

		if (principal.getId() != application.getPosition().getCompany().getId())
			result = new ModelAndView("security/hacking");
		else {
			result = new ModelAndView("application/display");
			result.addObject("application", application);
			result.addObject("permiso", true);
			result.addObject("curricula", curricula);
		}

		return result;
	}

	// Accept an application

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView res;
		Application application;
		final Company principal = this.companyService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));

		try {
			Assert.isTrue(application.getPosition().getCompany().equals(principal));
			try {

				this.applicationService.accept(application);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:list.do");
				res.addObject("message", "application.accept.error");
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}

	// Reject an application

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView res;
		Application application;
		final Company principal = this.companyService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));

		try {
			Assert.isTrue(application.getPosition().getCompany().equals(principal));
			try {
				this.applicationService.reject(application);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:list.do");
				res.addObject("message", "application.reject.error");
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}
}
