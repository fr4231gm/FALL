
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Problem;
import forms.ProblemForm;

@Controller
@RequestMapping("/problem")
public class ProblemCompanyController extends AbstractController {

	//Services
	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;


	//Constructor
	public ProblemCompanyController() {
		super();
	}
	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Company principal = this.companyService.findByPrincipal();
		final Collection<Problem> problems = this.problemService.findProblemsByCompanyId(principal.getId());

		res = new ModelAndView("problem/list");
		res.addObject("problems", problems);
		res.addObject("requestURI", "problem/list.do");
		return res;
	}

	//ListError
	@RequestMapping(value = "/listError", method = RequestMethod.GET)
	public ModelAndView listError(final boolean messageError) {
		ModelAndView res;
		res = new ModelAndView("problem/list");
		final Company principal = this.companyService.findByPrincipal();
		final Collection<Problem> problems = this.problemService.findProblemsByCompanyId(principal.getId());
		res.addObject("error", messageError);
		res.addObject("problems", problems);
		res.addObject("messageError", "message.error.delete");
		return res;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final ProblemForm problemForm = new ProblemForm();
		problemForm.setId(0);

		res = this.createEditModelAndView(problemForm);
		return res;
	}
	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int problemId) {
		ModelAndView res;
		Problem problem;

		try {
			problem = this.problemService.findOne(problemId);
			res = new ModelAndView("problem/display");
			res.addObject("problem", problem);
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notFind");
		}
		return res;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int problemId) {
		ModelAndView res;
		final Company principal = this.companyService.findByPrincipal();
		Problem problem = this.problemService.findOneToFail(problemId);
		if (problemId != 0 && this.problemService.findOneToFail(problemId) == null)
			res = new ModelAndView("security/notfind");
		else if (problem.getId() != 0 && problem.getCompany().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else if (problem.getIsDraft() == false)
			res = new ModelAndView("security/hacking");
		else
			try {
				problem = this.problemService.findOne(problemId);
				Assert.notNull(problem);
				final ProblemForm problemForm = this.problemService.construct(problem);
				res = this.createEditModelAndView(problemForm);
			} catch (final Throwable oops) {
				res = new ModelAndView();
			}
		return res;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final ProblemForm problemForm, final BindingResult binding) {
		ModelAndView res;
		Problem problem;
		if (problemForm.getId() != 0 && this.problemService.findOneToFail(problemForm.getId()) == null)
			res = new ModelAndView("security/notfind");
		else {
			problem = this.problemService.reconstruct(problemForm, binding);
			if (binding.hasErrors())
				res = this.createEditModelAndView(problemForm);
			else
				try {
					this.problemService.save(problem);
					res = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					res = this.createEditModelAndView(problemForm, "problem.commit.save.error");
				}
		}
		return res;
	}
	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int problemId) {
		ModelAndView res;
		final Company principal = this.companyService.findByPrincipal();
		Problem problem = this.problemService.findOneToFail(problemId);
		if (problemId != 0 && this.problemService.findOneToFail(problemId) == null)
			res = new ModelAndView("security/notfind");
		else if (problem.getId() != 0 && problem.getCompany().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				problem = this.problemService.findOne(problemId);
				this.problemService.delete(problem);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:listError.do");
				res.addObject("messageError", true);
			}
		return res;
	}

	//Ancillary methods--------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProblemForm problemForm) {
		ModelAndView res;
		res = this.createEditModelAndView(problemForm, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final ProblemForm problemForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("problem/edit");
		res.addObject("problemForm", problemForm);
		res.addObject("message", messageCode);
		return res;
	}
}
