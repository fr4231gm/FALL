package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	// Create
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		SubmissionForm submissionForm = new SubmissionForm();
		

		res = new ModelAndView("submission/assign");
		
		res = this.createEditModelAndView(submissionForm);
		
		return res;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid @ModelAttribute("submissionForm") final SubmissionForm submissionForm, final int submissionId,
			final BindingResult binding) {
		ModelAndView res;
		Collection<Reviewer> reviewers;
		
		reviewers = submissionForm.getReviewers();
		
		if (binding.hasErrors())
			res = this.createEditModelAndView(submissionForm);
		else
			try {
				this.submissionService.setRev(this.submissionService.findOne(submissionId), reviewers);
				res = new ModelAndView("redirect:/submission/administrator/list.do");
			}

			catch (final Throwable oops) {
				res = this.createEditModelAndView(submissionForm, "submission.commit.error");
			}
		return res;
	}
	
	@RequestMapping(value = "/autoassign", method = RequestMethod.GET)
	public ModelAndView autoassign(@Valid final SubmissionForm submissionForm, final int submissionId,
			final BindingResult binding) {
		ModelAndView res;
		Collection<Reviewer> reviewers;
		
		reviewers = this.conferenceService.getCompatibleReviewers(this.submissionService.findOne(submissionId).getConference());
				
		if (binding.hasErrors())
			res = this.createEditModelAndView(submissionForm);
		else
			try {
				this.submissionService.setRev(this.submissionService.findOne(submissionId), reviewers);
				res = new ModelAndView("redirect:/submission/administrator/list.do");
			}

			catch (final Throwable oops) {
				res = this.createEditModelAndView(submissionForm, "submission.commit.error");
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
			res = this.createEditModelAndView(submission, "submission.decide.error");
			res = new ModelAndView("redirect:list.do");
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
