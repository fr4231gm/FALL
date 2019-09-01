
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

import services.CurriculaService;
import services.EducationDataService;
import services.RookieService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;
import forms.EducationDataForm;

@Controller
@RequestMapping("/educationData/rookie")
public class EducationDataRookieController extends AbstractController {

	// Services
	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private RookieService			rookieService;


	// Constructors
	public EducationDataRookieController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final EducationDataForm res = new EducationDataForm();

		result = this.createEditModelAndView(res);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		ModelAndView result;
		final EducationData ed;
		final Rookie principal = this.rookieService.findByPrincipal();
		final Curricula c = this.curriculaService.findByEducationDataId(educationDataId);
		try {
			ed = this.educationDataService.findOneToEdit(educationDataId);
			Assert.isTrue(c.getRookie().equals(principal));
			Assert.notNull(ed);
			final EducationDataForm res = this.educationDataService.contruct(ed.getId());
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;
		final EducationData ed;
		ed = this.educationDataService.reconstruct(educationDataForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(educationDataForm);
		else
			try {
				this.educationDataService.save(ed, educationDataForm.getCurricula().getId());
				result = new ModelAndView("redirect:/curricula/rookie/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationDataForm, "ed.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;
		try {
			this.educationDataService.delete(educationDataForm.getId());
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationDataForm, "ed.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final EducationDataForm ed) {
		ModelAndView result;

		result = this.createEditModelAndView(ed, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationDataForm ed, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Curricula curricula;
		Collection<Curricula> curriculas;

		curricula = this.curriculaService.findByEducationDataId(ed.getId());
		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		if (ed.getId() == 0)
			permission = true;
		else
			for (final EducationData miscRec : curricula.getEducationData())
				if (ed.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("educationData/edit");
		result.addObject("educationDataForm", ed);
		result.addObject("permission", permission);
		result.addObject("curricula", curricula);
		result.addObject("curriculas", curriculas);
		result.addObject("message", messageCode);

		return result;

	}

}
