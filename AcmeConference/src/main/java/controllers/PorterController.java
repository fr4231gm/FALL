
package controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.PorterService;
import domain.Administrator;
import domain.Porter;

@Controller
@RequestMapping("/porter")
public class PorterController extends AbstractController {

	@Autowired
	private PorterService			porterService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int porterId) {
		ModelAndView res;
		Porter porter;

		porter = this.porterService.findOne(porterId);

		if (porter.getIsDraft().equals(true)) {
			final Administrator principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);
		}

		res = new ModelAndView("porter/display");
		res.addObject("porter", porter);
		res.addObject("conference", porter.getConference());

		return res;
	}

	//List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView res;
		final Collection<Porter> porters = this.porterService.findByConference(conferenceId);
		final Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.MONTH, -1);
		final Date oneMonth = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		final Date twoMonths = cal.getTime();
		res = new ModelAndView("porter/list");
		res.addObject("oneMonth", oneMonth);
		res.addObject("twoMonths", twoMonths);
		res.addObject("porters", porters);
		res.addObject("requestURI", "porter/list.do");
		return res;
	}
}
