package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/administrator")
public class ReportAdministratorController extends AbstractController {
	
	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int submissionId) {
		ModelAndView res;
		Collection<Report> reports;

		reports = this.reportService.findReportsBySubmissionId(submissionId);

		res = new ModelAndView("report/list");
		res.addObject("reports", reports);
		res.addObject("requestURI", "report/administrator/list.do");
		
		return res;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int reportId) {
		ModelAndView result;
		
		try { 
			Report report;
			report = this.reportService.findOneToDisplay(reportId);
			result = new ModelAndView("report/display");
			result.addObject(report);

		} catch(Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("message", "commit.error");
		}
		return result;
	}
	
}