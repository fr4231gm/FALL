package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReviewerService;
import services.SubmissionService;

import controllers.AbstractController;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	// Services
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private ReviewerService reviewerService;

	// Create
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int submissionId) {
		ModelAndView res;
		Collection <Reviewer> reviewers;
		Submission submission;		
		
		reviewers = this.reviewerService.findAll();
		submission = this.submissionService.findOne(submissionId);	

		res = new ModelAndView("submission/assign");
		res.addObject("reviewers", reviewers);
		res.addObject("submission", submission);
		
		return res;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Submission s,
			final BindingResult binding) {
		ModelAndView res;
		Submission aux;
		
		if (binding.hasErrors())
			res = this.createEditModelAndView(s);
		else
			try {

				aux = this.submissionService.saveAssign(s);
				res = new ModelAndView("redirect:display.do?submissionId="
						+ aux.getId());
			}

			catch (final Throwable oops) {
				res = this.createEditModelAndView(s, "submission.commit.error");
			}
		return res;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> subs;

		subs = this.submissionService.findAll();

		result = new ModelAndView("submission/list");

		result.addObject("actual", new Date(System.currentTimeMillis()));
		result.addObject("submissions", subs);
		result.addObject("requestURI", "submission/administrator/list.do");

		return result;
	}

	// Show
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "submissionId" })
	public ModelAndView display(@RequestParam final int submissionId) {
		ModelAndView res;

		// Initialize variables
		Submission s;
		s = this.submissionService.findOne(submissionId);

		res = new ModelAndView("submission/display");
		res.addObject("submission", s);

		return res;
	}

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final Submission s) {
		ModelAndView result;
		result = this.createEditModelAndView(s, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Submission s,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("submission/edit");
		result.addObject("submission", s);
		result.addObject("message", messageCode);

		return result;
	}
}
