
package controllers.company;

import java.util.Calendar;
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

import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionForm;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ProblemService	problemService;


	// Constructors -----------------------------------------------------------
	public PositionCompanyController() {
		super();
	}

	// RF 9.1 As a Company, manage their positions, which includes listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Position> positions;

		// for(Position positions:positionss){
		// if(positions.getIsCancelled() == false){

		final Company principal = this.companyService.findByPrincipal();
		positions = this.positionService.findPositionsNotCancelled(principal.getId());

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("permiso", true);
		result.addObject("requestURI", "position/company/list.do");
		// }
		// }

		return result;
	}

	// RF 9.1 As a Company, manage their positions, which includes showing

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"positionId"
	})
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;

		// Initialize variables
		Position position;

		position = this.positionService.findOne(positionId);

		result = new ModelAndView("position/display");
		result.addObject("position", position);
		return result;
	}

	// CREATE

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final Company principal = this.companyService.findByPrincipal();
		ModelAndView res;
		final Position p = this.positionService.create();
		final PositionForm positionForm = this.positionService.construct(p);
		Collection<Problem> problems;
		problems = this.problemService.findProblemsByCompanyId(principal.getId());

		res = new ModelAndView("position/company/create");
		res.addObject("positionForm", positionForm);
		res.addObject("problems", problems);

		return res;
	}

	//cancelar position
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId) {

		ModelAndView res;
		final Position p = this.positionService.findOne(positionId);
		Collection<Position> positions;
		Company principal;
		principal = this.companyService.findByPrincipal();

		try {
			p.setIsCancelled(true);
			this.positionService.save(p);

			positions = this.positionService.findPositionsNotCancelled(principal.getId());

			res = new ModelAndView("position/list");
			res.addObject("positions", positions);
			res.addObject("requestURI", "position/company/list.do");
			res.addObject("permiso", true);

		} catch (final Throwable oops) {

			principal = this.companyService.findByPrincipal();
			positions = this.positionService.findPositionsNotCancelled(principal.getId());

			res = new ModelAndView("position/list");
			res.addObject("positions", positions);
			res.addObject("requestURI", "position/company/list.do");
			res.addObject("permiso", true);
		}

		return res;
	}

	// Save create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final PositionForm positionForm, final BindingResult binding) {
		ModelAndView result;
		String messageCode;
		Company principal;
		Calendar currentTime;
		principal = this.companyService.findByPrincipal();

		currentTime = Calendar.getInstance();
		if (positionForm.getDeadline() != null)
			if (!(positionForm.getDeadline().after(currentTime.getTime())))
				binding.rejectValue("deadline", "position.pastDate");
		if (positionForm.getId() != 0 && this.positionService.findOneToFail(positionForm.getId()) == null)
			result = new ModelAndView("security/notfind");
		else {

			Collection<Problem> problems;
			problems = this.problemService.findProblemsByCompanyId(principal.getId());

			if (binding.hasErrors()) {
				result = new ModelAndView();
				result.addObject("problems", problems);
				result.addObject("bind", binding);
			} else
				try {
					final Position toSave = this.positionService.reconstruct(positionForm, binding);
					if (!positionForm.getIsDraft())
						toSave.setIsDraft(false);
					toSave.setIsCancelled(false);
					this.positionService.save(toSave);

					result = new ModelAndView("redirect:/position/company/list.do");
					messageCode = "position.success";
				} catch (final Throwable oops) {
					result = new ModelAndView();
					messageCode = "position.commit.error";
					result.addObject("problems", problems);
					result.addObject("message", messageCode);
				}
		}

		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int positionId) {
		ModelAndView res;
		final Company principal = this.companyService.findByPrincipal();
		PositionForm positionForm;

		Collection<Problem> problems;
		problems = this.problemService.findProblemsByCompanyId(principal.getId());
		if (positionId != 0 && this.positionService.findOneToFail(positionId) == null)
			res = new ModelAndView("security/notfind");
		else
			try {
				final Position position = this.positionService.findOne(positionId);
				Assert.isTrue(position.getCompany() == principal && position.getIsDraft());

				try {

					positionForm = this.positionService.construct(this.positionService.findOne(positionId));
					res = this.createEditModelAndView(positionForm);
					res.addObject("problems", problems);
				} catch (final Throwable oops) {
					res = new ModelAndView();
				}
			} catch (final Throwable oops) {
				res = new ModelAndView("security/hacking");
			}
		return res;
	}

	// SAVE

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = {
		"save"
	})
	public ModelAndView save(final PositionForm positionForm, final BindingResult binding) {

		ModelAndView result;
		String messageCode;
		Company principal;
		Calendar currentTime;
		currentTime = Calendar.getInstance();
		principal = this.companyService.findByPrincipal();
		if (positionForm.getDeadline() != null)
			if (!(positionForm.getDeadline().after(currentTime.getTime())))
				binding.rejectValue("deadline", "position.pastDate");
		if (positionForm.getId() != 0 && this.positionService.findOneToFail(positionForm.getId()) == null)
			result = new ModelAndView("security/notfind");
		else {
			final Position toSave = this.positionService.reconstruct(positionForm, binding);
			Collection<Problem> problems;
			problems = this.problemService.findProblemsByCompanyId(principal.getId());

			if (binding.hasErrors()) {
				result = new ModelAndView();
				result.addObject("problems", problems);
				result.addObject("bind", binding);
			} else
				try {
					if (positionForm.getIsDraft() == false)
						Assert.isTrue(toSave.getProblems().size() >= 2);

					this.positionService.save(toSave);
					result = new ModelAndView("redirect:/position/company/list.do");
					messageCode = "position.success";
				} catch (final Throwable oops) {
					result = new ModelAndView();

					messageCode = "position.commit.error";
					result.addObject("message", messageCode);
				}

			result.addObject("problems", problems);
		}
		return result;
	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Position positionId) {

		ModelAndView res;

		// Create the ModelAndView

		try {

			this.positionService.delete(positionId);
			res = new ModelAndView("redirect:/position/company/list.do");

		} catch (final Throwable oops) {
			Collection<Position> positions;
			Company principal;
			principal = this.companyService.findByPrincipal();
			positions = this.positionService.findPositionsNotCancelled(principal.getId());

			res = new ModelAndView("position/list");
			res.addObject("positions", positions);
			res.addObject("requestURI", "position/company/list.do");
		}
		return res;

	}

	// Ancillary metods

	protected ModelAndView createEditModelAndView(final PositionForm position) {
		ModelAndView result;
		result = this.createEditModelAndView(position, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionForm position, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("position/company/edit");
		result.addObject("positionForm", position);
		result.addObject("message", messageCode);

		return result;
	}
}
