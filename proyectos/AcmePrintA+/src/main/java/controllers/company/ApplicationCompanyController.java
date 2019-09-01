
package controllers.company;

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

import services.ApplicationService;
import services.CompanyService;
import services.RequestService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Request;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private RequestService		requestService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Application> applications;
		Company principal;

		principal = this.companyService.findByPrincipal();

		applications = this.applicationService.findApplicationsByCompanyId(principal.getId());

		res = new ModelAndView("application/list");
		res.addObject("applications", applications);
		res.addObject("requestURI", "application/company/list.do");
		res.addObject("permiso", true);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int orderId) {
		ModelAndView res;
		Application application;

		application = this.applicationService.create(orderId);

		res = this.createEditModelAndView(application);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView res;
		Application aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(application);
		else
			try {
				Assert.isNull(this.applicationService.findAcceptedApplicationByOrderId(application.getOrder().getId()));
				aux = this.applicationService.save(application);
				res = new ModelAndView("redirect:display.do?applicationId=" + aux.getId());

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(application, "application.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;

		// Initialize variables
		Application application;
		Company principal;

		principal = this.companyService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);

		if (principal.getId() != application.getCompany().getId())
			result = new ModelAndView("security/hacking");
		else {
			result = new ModelAndView("application/display");
			result.addObject("application", application);
			result.addObject("permiso", true);
			final Request r = this.requestService.findByOrderId(application.getOrder().getId());
			if (r == null)
				result.addObject("encolable", true);
			else
				result.addObject("posicion", r.getNumber());
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

}
