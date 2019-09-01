
package controllers;

import java.util.ArrayList;
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

import services.ApplicationService;
import services.ConfigurationService;
import services.FixUpTaskService;
import domain.Application;
import domain.FixUpTask;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ConfigurationService	configService;


	// Constructors -----------------------------------------------------------

	public ApplicationCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView findByCertificationId(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		Collection<Application> applications;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(fixUpTask);
		applications = fixUpTask.getApplications();

		result = new ModelAndView("application/customer/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/list.do?fixUpTaskId=" + fixUpTaskId);
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

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;

		Application application;
		application = this.applicationService.findById(applicationId);
		result = this.createEditModelAndView(application);
		return result;

	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		Application old = this.applicationService.findById(application.getId());
		final int s = application.getFixUpTask().getId();
			try {
	
				System.out.println(application.getStatus());
				if(application.getStatus().equals("ACCEPTED")){
					System.out.println("bien");
					result = new ModelAndView("redirect:creditcard/customer/createforapp.do");
					result.addObject(application);
				} else {
		
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do?fixUpTaskId=" + s);
				}
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(application, "application.commit.error");
			}
		return result;

	}
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {

		final ModelAndView result = new ModelAndView("application/customer/edit");

		Assert.notNull(application);
		final int fixUpTaskId = application.getFixUpTask().getId();
		final int applicationId = application.getId();
		final Collection<String> status = new ArrayList<String>();
		status.add("PENDING");
		status.add("ACCEPTED");
		status.add("REJECTED");
		result.addObject("actionURI", "application/customer/edit.do?applicationId=" + applicationId + "&fixuptaskId=" + fixUpTaskId);
		result.addObject("cancelURI", "application/customer/list.do?fixUpTaskId=" + fixUpTaskId); //creo que esto no se puede hacer
		result.addObject("application", application);
		result.addObject("dateSystem", new Date());
		result.addObject("message", messageCode);
		result.addObject("status", status);
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}

}
