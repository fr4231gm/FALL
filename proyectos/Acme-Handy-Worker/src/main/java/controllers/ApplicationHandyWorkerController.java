
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.ConfigurationService;
import services.FixUpTaskService;
import domain.Application;

@Controller
@RequestMapping("/application/handyworker")
public class ApplicationHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ConfigurationService	configService;


	// Constructors -----------------------------------------------------------

	public ApplicationHandyWorkerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;

		applications = this.applicationService.findByHandyWorker(this.actorService.findByPrincipal().getId());
		Assert.notNull(applications);

		result = new ModelAndView("application/handyworker/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyworker/list.do");
		result.addObject("dateSystem", new Date());

		return result;
	}

	// Show

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView findByApplicationId(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findById(applicationId);
		Assert.notNull(application);

		result = new ModelAndView("application/handyworker/show"); //¿aquí hay que poner application/handyworker/show ?
		result.addObject("application", application);
		result.addObject("requestURI", "application/handyworker/show.do?applicationId=" + applicationId);

		return result;
	}

	// Create 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixuptaskId) {
		final ModelAndView result;

		Application application;
		application = this.applicationService.create();
		application.setFixUpTask(this.fixUpTaskService.findOne(fixuptaskId));
		result = this.createEditModelAndView(application);
		return result;

	}
	// Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(application.getFixUpTask());
		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else

				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");


		return result;
	}
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		final ModelAndView result = new ModelAndView("application/handyworker/create");
		Assert.notNull(application);
		// actionURI y cancelURI
		result.addObject("actionURI", "application/handyworker/create.do");
		result.addObject("cancelURI", "application/handyworker/list.do");
		result.addObject("application", application);
		result.addObject("message", messageCode);
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}

}
