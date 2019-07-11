
package controllers.author;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SubmissionService;
import controllers.AbstractController;
import domain.Submission;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	//Services
	@Autowired
	private SubmissionService	submissionService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> subs;

		subs = this.submissionService.findByAuthor();

		result = new ModelAndView("submission/list");

		result.addObject("submissions", subs);
		result.addObject("requestURI", "submission/author/list.do");

		return result;
	}

	//Show
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"submissionId"
	})
	public ModelAndView display(@RequestParam final int submissionId) {
		ModelAndView res;

		// Initialize variables
		Submission s;
		s = this.submissionService.findOne(submissionId);

		res = new ModelAndView("submission/display");
		res.addObject("submission", s);

		return res;
	}

}
