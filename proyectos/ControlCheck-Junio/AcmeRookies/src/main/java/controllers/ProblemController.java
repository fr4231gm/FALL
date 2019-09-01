
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProblemService;
import domain.Problem;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

	//Services
	@Autowired
	private ProblemService	problemService;


	//Constructor
	public ProblemController() {
		super();
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
			res = new ModelAndView("security/notfind");
		}
		return res;
	}

}
