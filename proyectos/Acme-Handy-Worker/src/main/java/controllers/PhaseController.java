
package controllers;

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
import services.PhaseService;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyworker")
public class PhaseController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public PhaseController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int applicationId) {
		ModelAndView result;
		Collection<Phase> phases;

		phases = this.phaseService.findPhasesByApplication(applicationId);
		Assert.notNull(phases);

		result = new ModelAndView("phase/handyworker/list");
		result.addObject("phases", phases);
		result.addObject("requestURI", "phase/handyworker/list.do");
		result.addObject("application", this.applicationService.findById(applicationId));

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showPhase(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;
		phase = this.phaseService.findOnde(phaseId);
		result = new ModelAndView("phase/handyworker/show");
		result.addObject("phase", phase);
		return result;
	}

	// Create 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;

		Phase phase;
		phase = this.phaseService.create();
		phase.setApplication(this.applicationService.findById(applicationId));
		result = this.createEditModelAndView(phase);
		return result;

	}

	//Edito
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;

		phase = this.phaseService.findOnde(phaseId);
		Assert.notNull(phase);
		result = this.createEditModelAndView(phase);

		return result;

	}
	// Delete

	@RequestMapping(value = "/remove", method = RequestMethod.GET, params = {
		"id"
	})
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		final Collection<Phase> phases;
		try {
			this.phaseService.delete(this.phaseService.findOnde(id));
			phases = this.phaseService.findAll();
			result = new ModelAndView("phase/handyworker/list");
			result.addObject("phases", phases);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/phase/list.do");
		}
		return result;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:list.do?applicationId=" + phase.getApplication().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "application.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else

			this.phaseService.save(phase);
		result = new ModelAndView("redirect:list.do?applicationId=" + phase.getApplication().getId());

		return result;
	}
	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String messageCode) {
		final ModelAndView result = new ModelAndView("phase/handyworker/edit");
		Assert.notNull(phase);
		result.addObject("phase", phase);
		result.addObject("message", messageCode);
		result.addObject("application", phase.getApplication());
		return result;
	}

}
