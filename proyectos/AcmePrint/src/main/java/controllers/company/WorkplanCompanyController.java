
package controllers.company;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.WorkPlanService;
import controllers.AbstractController;
import domain.Company;
import domain.Phase;
import domain.WorkPlan;

@Controller
@RequestMapping("/workplan/company")
public class WorkplanCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private WorkPlanService	workplanService;

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/workplan", method = RequestMethod.GET)
	public ModelAndView workplan(@RequestParam final int applicationId) {
		ModelAndView res;

		if (this.workplanService.findByApplicationId(applicationId) == null)
			res = new ModelAndView("redirect:confirmCreate.do?applicationId=" + applicationId);
		else
			res = new ModelAndView("redirect:display.do?applicationId=" + applicationId);
		return res;
	}

	@RequestMapping(value = "/confirmCreate", method = RequestMethod.GET)
	public ModelAndView confirmCreate(@RequestParam final int applicationId) {
		final ModelAndView res;

		res = new ModelAndView("workplan/confirmCreate");
		res.addObject("id", applicationId);
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		final ModelAndView res;
		WorkPlan workplan;
		workplan = this.workplanService.create(applicationId);
		this.workplanService.createPhases(workplan);
		//		this.workplanService.save(workplan);

		res = new ModelAndView("redirect:display.do?applicationId=" + applicationId);

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;

		// Initialize variables
		WorkPlan workplan;
		Company principal;
		final Collection<Phase> phases = new ArrayList<Phase>();

		principal = this.companyService.findByPrincipal();
		workplan = this.workplanService.findByApplicationId(applicationId);
		phases.addAll(workplan.getPhases());

		if (principal.getId() != workplan.getApplication().getCompany().getId())
			result = new ModelAndView("security/hacking");
		else {
			result = new ModelAndView("workplan/display");
			result.addObject("requestURI", "/workplan/company/display.do");
			result.addObject("workplan", workplan);
			result.addObject("phases", phases);
			result.addObject("permiso", true);
		}

		return result;
	}

}
