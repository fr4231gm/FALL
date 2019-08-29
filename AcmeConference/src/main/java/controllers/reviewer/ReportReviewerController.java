
package controllers.reviewer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/reviewer")
public class ReportReviewerController extends AbstractController {

	@Autowired
	private ReportService		reportService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ReviewerService		reviewerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Report> reports;

		reports = this.reportService.findByPrincipal();

		res = new ModelAndView("report/list");
		res.addObject("reports", reports);
		res.addObject("requestURI", "report/reviewer/list.do");

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int submissionId) {
		ModelAndView result;

		try {
			Report report;
			report = this.reportService.create(submissionId);
			Assert.isTrue(this.reviewerService.findByPrincipal().getSubmissions().contains(this.submissionService.findOne(submissionId)));
			result = new ModelAndView("report/create");
			result.addObject(report);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("message", "commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				result = new ModelAndView("report/create");
				result.addObject(report);
				result.addObject("bind", binding);
			} else {
				final Report saved = this.reportService.save(report);
				result = new ModelAndView("redirect:/report/reviewer/display.do?reportId=" + saved.getId());
				result.addObject(report);
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("message", "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		ModelAndView result;

		try {
			Report report;
			report = this.reportService.findOneToDisplay(reportId);
			result = new ModelAndView("report/display");
			result.addObject(report);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("message", "commit.error");
		}
		return result;
	}

}
