
package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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


	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Submission sub = this.submissionService.create();

		res = this.createEditModelAndView(sub);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Submission s, final BindingResult binding) {
		ModelAndView res;
		Submission aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(s);
		else
			try {

				aux = this.submissionService.save(s);
				res = new ModelAndView("redirect:display.do?applicationId=" + aux.getId());

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(s, "submission.commit.error");
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
