
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.QuoletService;
import domain.Administrator;
import domain.Quolet;

@Controller
@RequestMapping("/quolet")
public class QuoletController extends AbstractController {

	@Autowired
	private QuoletService			quoletService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AdministratorService	adminService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int quoletId) {
		ModelAndView res;
		Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);

		if (quolet.getIsDraft().equals(false)) {
			final Administrator principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);
		}

		res = new ModelAndView("quolet/display");
		res.addObject("quolet", quolet);
		res.addObject("conference", quolet.getConference());

		return res;
	}

	//List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView res;
		final Collection<Quolet> quolets = this.quoletService.findByConference(conferenceId);
		res = new ModelAndView("quolet/list");
		res.addObject("quolets", quolets);
		res.addObject("requestURI", "quolet/list.do");
		return res;
	}
}
