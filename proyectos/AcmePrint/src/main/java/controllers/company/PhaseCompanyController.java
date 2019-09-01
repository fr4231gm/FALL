
package controllers.company;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.PhaseService;
import services.WorkPlanService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Phase;

@Controller
@RequestMapping("/phase/company")
public class PhaseCompanyController extends AbstractController {

	// Services
	@Autowired
	private PhaseService	phaseService;

	@Autowired
	private WorkPlanService	workPlanService;

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int workPlanId) {
		ModelAndView res;
		Phase phase;

		phase = this.phaseService.create(this.workPlanService.findOne(workPlanId));

		res = this.createEditModelAndView(phase);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(phase);
		else
			try {

				this.phaseService.save(phase);
				res = new ModelAndView("redirect:/workplan/company/display.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(phase, "application.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView res;
		Phase phase;
		Company principal;

		phase = this.phaseService.findOne(phaseId);
		principal = this.companyService.findByPrincipal();

		if (principal.getId() != this.phaseService.findApplication(this.phaseService.findOne(phaseId)).getCompany().getId())
			res = new ModelAndView("security/hacking");
		else
			try {

				res = new ModelAndView("phase/edit");
				res.addObject("phase", phase);

			} catch (final Throwable oops) {

				res = new ModelAndView("phase/edit");
				res.addObject("phase", phase);
				res.addObject("message", "application.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int phaseId) {
		ModelAndView result;

		// Initialize variables
		Application application;
		Company principal;

		principal = this.companyService.findByPrincipal();
		application = this.phaseService.findApplication(this.phaseService.findOne(phaseId));
		final Phase p = this.phaseService.findOne(phaseId);
		if (principal.getId() != application.getCompany().getId())
			result = new ModelAndView("security/hacking");
		else {
			result = new ModelAndView("phase/display");
			result.addObject("phase", p);
			result.addObject("permiso", true);
		}

		return result;
	}
	//Save edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView res;
		Phase aux;

		if (binding.hasErrors()) {
			res = new ModelAndView("phase/edit");
			res.addObject("phase", phase);
		} else
			try {
				if (phase.getIsDone() == true)
					phase.setMoment(new Date(System.currentTimeMillis() - 1));
				aux = this.phaseService.save(phase);
				res = new ModelAndView("redirect:/workplan/company/display.do?applicationId=" + this.phaseService.findApplication(phase).getId());
				res.addObject("application", aux);

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(phase, "application.commit.error");
			}
		return res;
	}
	// Ancillary metods

	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;
		result = this.createEditModelAndView(phase, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("message", messageCode);

		return result;
	}
}
