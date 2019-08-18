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

import services.ConferenceService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Reviewer;
import domain.Submission;
import forms.SubmissionForm;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	// Services
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private ReviewerService reviewerService;
	
	@Autowired
	private ConferenceService conferenceService;

	// Other Methods
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int submissionId) {
		ModelAndView res;
		SubmissionForm submissionForm = new SubmissionForm();
		submissionForm.setSubmission(this.submissionService.findOne(submissionId));
		res = new ModelAndView("submission/assign");
		res = this.createEditModelAndView(submissionForm);
		
		return res;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView assignPost(@Valid final SubmissionForm submissionForm, final BindingResult binding) {
		ModelAndView res;
		try {
			if(binding.hasErrors()){
				res = this.createEditModelAndView(submissionForm);
			} else {
				this.submissionService.setRev(submissionForm.getSubmission(), submissionForm.getReviewers());
				res = new ModelAndView("redirect:/submission/administrator/list.do");
			}

		} catch (final Throwable oops) {
			res = this.createEditModelAndView(submissionForm, "submission.commit.error");
		}
		return res;
	}
	
	@RequestMapping(value = "/autoassign", method = RequestMethod.GET)
	public ModelAndView autoassign(final int submissionId) {
		ModelAndView res;
		Collection<Reviewer> reviewers;
		
		try {
			reviewers = this.conferenceService.getCompatibleReviewers(this.submissionService.findOne(submissionId).getConference());
			this.submissionService.setRev(this.submissionService.findOne(submissionId), reviewers);
			res = new ModelAndView("redirect:/submission/administrator/list.do");
		}
		catch (final Throwable oops) {
			res = new ModelAndView("redirect:/submission/administrator/list.do");
			res.addObject("message", "submission.commit.error");
		}
		return res;
	}
	
	@RequestMapping(value = "/decide", method = RequestMethod.GET)
	public ModelAndView decide(@Valid final Submission submission,
			final BindingResult binding) {
		ModelAndView res;		
		
		try {
			this.submissionService.decide(submission);
			res = new ModelAndView("redirect:list.do");
		}
		catch (final Throwable oops) {
			res = new ModelAndView("redirect:list.do");
			res.addObject("message", "submission.commit.error");
		}
		
		return res;
	}

	// Display
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
		Boolean decide = false;
		Date actual = new Date(System.currentTimeMillis());
		
		if(this.submissionService.findOne(submissionId).getConference().getSubmissionDeadline().before(actual)){
			decide = true;
		}

		// Initialize variables
		Submission s;
		s = this.submissionService.findOne(submissionId);

		res = new ModelAndView("submission/display");
		res.addObject("submission", s);
		res.addObject("decide", decide);

		return res;
	}

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final SubmissionForm submissionForm) {
		final ModelAndView result = this.createEditModelAndView(submissionForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SubmissionForm submissionForm,
			final String messagecode) {
		ModelAndView result;
		
		final Collection<Reviewer> reviewers = this.reviewerService.findAll();
		
		result = new ModelAndView("submission/assign");
		result.addObject("submissionForm", submissionForm);
		result.addObject("reviewers", reviewers);
		result.addObject("message", messagecode);

		return result;
	}
}
