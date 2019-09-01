
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
import services.RookieService;
import services.MiscellaneousDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Rookie;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Controller
@RequestMapping("/miscellaneousData/rookie")
public class MiscellaneousDataRookieController extends AbstractController {

	// Services
	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private RookieService				rookieService;


	// Constructors

	public MiscellaneousDataRookieController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final MiscellaneousDataForm res = new MiscellaneousDataForm();

		result = this.createEditModelAndView(res);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		final MiscellaneousData mr;
		final Rookie principal = this.rookieService.findByPrincipal();
		final Curricula c = this.curriculaService.findByMiscellaneousDataId(miscellaneousDataId);
		try {
			Assert.isTrue(principal.equals(c.getRookie()));
			mr = this.miscellaneousDataService.findOneToEdit(miscellaneousDataId);
			Assert.notNull(mr);
			final MiscellaneousDataForm res = this.miscellaneousDataService.contruct(mr.getId());
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		ModelAndView result;
		final MiscellaneousData mr;
		mr = this.miscellaneousDataService.reconstruct(miscellaneousDataForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousDataForm);
		else
			try {
				this.miscellaneousDataService.save(mr, miscellaneousDataForm.getCurricula().getId());
				result = new ModelAndView("redirect:/curricula/rookie/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousDataForm, "mr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousDataForm miscellaneousDataForm) {
		ModelAndView result;

		try {
			this.miscellaneousDataService.delete(miscellaneousDataForm.getId());
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousDataForm, "mr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm mr) {
		ModelAndView result;

		result = this.createEditModelAndView(mr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm mr, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Curricula curricula;
		Collection<Curricula> curriculas;

		curricula = this.curriculaService.findByMiscellaneousDataId(mr.getId());
		curriculas = this.curriculaService.findNonCopiesByPrincipal();

		if (mr.getId() == 0)
			permission = true;
		else
			for (final MiscellaneousData miscRec : curricula.getMiscellaneousData())
				if (mr.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("miscellaneousData/edit");
		result.addObject("miscellaneousDataForm", mr);
		result.addObject("permission", permission);
		result.addObject("curricula", curricula);
		result.addObject("curriculas", curriculas);

		result.addObject("message", messageCode);

		return result;

	}

}
