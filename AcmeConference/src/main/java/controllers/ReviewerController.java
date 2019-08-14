/*
 * LegalTermsController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReviewerService;
import domain.Reviewer;
import forms.ReviewerForm;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController extends AbstractController {

	@Autowired
	private ReviewerService reviewerService;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		Reviewer reviewer;

		reviewer = this.reviewerService.findByPrincipal();
		Assert.notNull(reviewer);
		res = this.createEditModelAndView(reviewer);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Reviewer reviewer, BindingResult binding) {
		ModelAndView res;
		Reviewer toSave;

		try {
			if (!(reviewer.getEmail()
					.matches("[A-Za-z_.]+[\\w]+[\\S]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
					&& reviewer.getEmail().length() > 0)
				binding.rejectValue("email", "actor.email.check");
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(reviewer);
			} else {
				toSave = this.reviewerService.save(reviewer);
				res = new ModelAndView("welcome/index");
				res.addObject("name", toSave.getName());
				res.addObject("exitCode", "actor.edit.success");
				res.addObject("moment",
						new SimpleDateFormat("dd/MM/yyyy HH:mm")
								.format(new Date()));
			}
		} catch (Throwable oops) {
			res = this
					.createEditModelAndView(reviewer, "reviewer.commit.error");
		}
		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		ReviewerForm reviewerForm;
		reviewerForm = new ReviewerForm();
		reviewerForm.setCheckTerms(false);
		result = new ModelAndView("reviewer/register");
		result.addObject("reviewerForm", reviewerForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(final ReviewerForm reviewerForm,
			final BindingResult binding) {
		// Initialize Variables
		ModelAndView result;
		Reviewer reviewer;

		// Create the reviewer object from the reviewerForm
		reviewer = this.reviewerService.reconstruct(reviewerForm, binding);
		try {
			// If the form has errors prints it
			if (binding.hasErrors()) {
				result = new ModelAndView("reviewer/register");
			} else {
				// If the form does not have errors, try to save it
				this.reviewerService.save(reviewer);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
				result.addObject("name", reviewer.getName());
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("reviewer/register");
			result.addObject("reviewerForm", reviewerForm);
			result.addObject("message", "actor.commit.error");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Reviewer> reviewers;
		
		reviewers = this.reviewerService.findAll();

		result = new ModelAndView("reviewer/list");

		result.addObject("reviewers", reviewers);
		result.addObject("requestURI", "reviewer/list.do");

		return result;
	}
	
	// List
		@RequestMapping(value = "/listBySubmission", method = RequestMethod.GET)
		public ModelAndView listBySubmission(@RequestParam int submissionId) {
			ModelAndView result;
			Collection<Reviewer> reviewers;

			reviewers = this.reviewerService.findReviewersBySubmission(submissionId);

			result = new ModelAndView("reviewer/list");

			result.addObject("reviewers", reviewers);
			result.addObject("requestURI", "reviewer/list.do");

			return result;
		}

	// Show
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "reviewerId" })
	public ModelAndView display(@RequestParam final int reviewerId) {
		ModelAndView res;

		// Initialize variables
		Reviewer r;
		r = this.reviewerService.findOne(reviewerId);

		res = new ModelAndView("reviewer/display");
		res.addObject("reviewer", r);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer) {
		final ModelAndView result = this.createEditModelAndView(reviewer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("reviewer/edit");
		result.addObject("reviewer", reviewer);
		result.addObject("message", messagecode);

		return result;
	}
}
