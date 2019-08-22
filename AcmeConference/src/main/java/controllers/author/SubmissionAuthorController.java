
package controllers.author;

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

import services.AuthorService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Submission;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	//Services
	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AuthorService		authorService;


	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView res;
		final Submission sub = this.submissionService.create(conferenceId);

		res = this.createEditModelAndView(sub);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Submission s, final BindingResult binding) {
		ModelAndView res;

		if(this.submissionService.findSubmissionByPaperTitle(s.getPaper().getTitle()) != null){
			binding.rejectValue("paper.title", "submission.paper.title.error");
		}

		if (binding.hasErrors())
			res = this.createEditModelAndView(s);
		else
			try {
				this.submissionService.save(s);
				res = new ModelAndView("redirect:list.do");
			}

			catch (final Throwable oops) {
				res = this.createEditModelAndView(s, "submission.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int submissionId) {
		ModelAndView res;
		final Submission s;
		Author principal;
		s = this.submissionService.findOne(submissionId);
		principal = this.authorService.findByPrincipal();

		if (s.getAuthor().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {

				res = new ModelAndView("submission/edit");
				res.addObject("submission", s);

			} catch (final Throwable oops) {

				res = new ModelAndView("submission/edit");
				res.addObject("submission", s);
				res.addObject("message", "submission.commit.error");
			}
		res.addObject("fecha", System.currentTimeMillis() - 1);
		return res;
	}

	//Save edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Submission submission, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = new ModelAndView("submission/edit");
			res.addObject("submission", submission);
			res.addObject("fecha", System.currentTimeMillis() - 1);
		} else
			try {

				this.submissionService.save(submission);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(submission, "submission.commit.error");
				res.addObject("fecha", System.currentTimeMillis() - 1);
			}
		return res;
	}
	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> subs;

		subs = this.submissionService.findByAuthor();

		result = new ModelAndView("submission/list");

		result.addObject("actual", new Date(System.currentTimeMillis()));
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

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final Submission s) {
		ModelAndView result;
		result = this.createEditModelAndView(s, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Submission s, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("submission/edit");
		result.addObject("submission", s);
		result.addObject("message", messageCode);

		return result;
	}

}
