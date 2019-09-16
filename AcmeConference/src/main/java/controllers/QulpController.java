
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
import services.QulpService;
import domain.Administrator;
import domain.Qulp;

@Controller
@RequestMapping("/qulp")
public class QulpController extends AbstractController {

	@Autowired
	private QulpService				qulpService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int qulpId) {
		ModelAndView res;
		Qulp qulp;

		qulp = this.qulpService.findOne(qulpId);

		if (qulp.getIsDraft().equals(true)) {
			final Administrator principal = this.administratorService.findByPrincipal();
			if(principal.getId() != qulp.getAdministrator().getId()) {
				Assert.notNull(principal);
				res = new ModelAndView("security/hacking");
			}else {
				res = new ModelAndView("qulp/display");
			}
		}else {
			res = new ModelAndView("qulp/display");
		}

		
		res.addObject("qulp", qulp);
		res.addObject("conference", qulp.getConference());

		return res;
	}

	//List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView res;
		final Collection<Qulp> qulps = this.qulpService.findByConference(conferenceId);
		final Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -1);
		final Date oneMonth = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		final Date twoMonths = cal.getTime();
		res = new ModelAndView("qulp/list");
		res.addObject("oneMonth", oneMonth);
		res.addObject("twoMonths", twoMonths);
		res.addObject("qulps", qulps);
		res.addObject("requestURI", "qulp/list.do");
		return res;
	}
}
