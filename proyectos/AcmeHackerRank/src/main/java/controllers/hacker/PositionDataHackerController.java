
package controllers.hacker;

import java.util.Collection;

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
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;
import forms.PositionDataForm;

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataHackerController extends AbstractController {

	// Services
	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private HackerService		hackerService;


	// Constructors
	public PositionDataHackerController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final PositionDataForm res = new PositionDataForm();

		result = this.createEditModelAndView(res);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionDataId) {
		ModelAndView result;
		final PositionData ed;
		final Hacker principal = this.hackerService.findByPrincipal();
		final Curricula c = this.curriculaService.findByPositionDataId(positionDataId);

		try {
			Assert.isTrue(principal.equals(c.getHacker()));
			ed = this.positionDataService.findOneToEdit(positionDataId);
			Assert.notNull(ed);
			final PositionDataForm res = this.positionDataService.contruct(ed.getId());
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PositionDataForm positionDataForm, final BindingResult binding) {
		ModelAndView result;
		final PositionData ed;
		ed = this.positionDataService.reconstruct(positionDataForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(positionDataForm);
		else
			try {
				this.positionDataService.save(ed, positionDataForm.getCurricula().getId());
				result = new ModelAndView("redirect:/curricula/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(positionDataForm, "ed.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionDataForm positionDataForm, final BindingResult binding) {
		ModelAndView result;
		try {
			this.positionDataService.delete(positionDataForm.getId());
			result = new ModelAndView("redirect:/curricula/hacker/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionDataForm, "ed.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final PositionDataForm ed) {
		ModelAndView result;

		result = this.createEditModelAndView(ed, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionDataForm ed, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Curricula curricula;
		Collection<Curricula> curriculas;

		curricula = this.curriculaService.findByPositionDataId(ed.getId());
		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		if (ed.getId() == 0)
			permission = true;
		else
			for (final PositionData miscRec : curricula.getPositionData())
				if (ed.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("positionData/edit");
		result.addObject("positionDataForm", ed);
		result.addObject("permission", permission);
		result.addObject("curricula", curricula);
		result.addObject("curriculas", curriculas);
		result.addObject("message", messageCode);

		return result;

	}

}
