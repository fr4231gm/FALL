package controllers.reviewer;

import java.util.Collection;
import java.util.Date;

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
@RequestMapping("/submission/reviewer")
public class SubmissionReviewerController extends AbstractController {

	// Services
	@Autowired
	private SubmissionService submissionService;
	

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> subs;
		
		subs = this.submissionService.findReportablesSubmissions();

		result = new ModelAndView("submission/list");

		result.addObject("actual", new Date());
		result.addObject("submissions", subs);
		result.addObject("requestURI", "submission/reviewer/list.do");
		result.addObject("reportable", true);

		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
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
