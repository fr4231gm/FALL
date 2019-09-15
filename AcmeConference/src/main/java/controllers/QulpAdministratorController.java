
package controllers;

import java.util.Calendar;
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

import services.AdministratorService;
import services.ConferenceService;
import services.QulpService;
import domain.Administrator;
import domain.Conference;
import domain.Qulp;

@Controller
@RequestMapping("/qulp/administrator")
public class QulpAdministratorController extends AbstractController {

	@Autowired
	private QulpService				qulpService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ConferenceService		conferenceService;


	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Qulp qulp = this.qulpService.create();

		res = this.createEditModelAndView(qulp);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Qulp qulp, final BindingResult binding) {
		ModelAndView res;

		if (qulp.getIsDraft() == null)
			qulp.setIsDraft(false);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(qulp);
			res.addObject("binding", binding);
		} else
			try {
				this.qulpService.save(qulp);
				res = new ModelAndView("redirect:list.do");
			}

			catch (final Throwable oops) {
				res = this.createEditModelAndView(qulp, "qulp.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int qulpId) {
		ModelAndView res;
		final Qulp qulp;
		Administrator principal;
		qulp = this.qulpService.findOne(qulpId);
		principal = this.administratorService.findByPrincipal();

		if (qulp.getAdministrator().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = new ModelAndView("qulp/edit");
				res.addObject("qulp", qulp);

			} catch (final Throwable oops) {

				res = new ModelAndView("qulp/edit");
				res.addObject("qulp", qulp);
				res.addObject("message", "qulp.commit.error");
			}
		res.addObject("conferences", this.conferenceService.findAllNoDraft());
		return res;
	}

	// Save edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Qulp qulp, final BindingResult binding) {
		ModelAndView res;

		if (qulp.getIsDraft() == null)
			qulp.setIsDraft(false);

		if (binding.hasErrors()) {
			res = new ModelAndView("qulp/edit");
			res.addObject("qulp", qulp);
		} else
			try {

				this.qulpService.save(qulp);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(qulp, "qulp.commit.error");
			}
		return res;
	}

	// List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		final Collection<Qulp> qulps = this.qulpService.findByAdministrator(this.administratorService.findByPrincipal().getId());
		final Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -1);
		final Date oneMonth = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		final Date twoMonths = cal.getTime();
		res = new ModelAndView("qulp/list");
		res.addObject("oneMonth", oneMonth);
		res.addObject("twoMonths", twoMonths);
		res.addObject("qulps", qulps);
		res.addObject("requestURI", "qulp/administrator/list.do");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int qulpId) {

		ModelAndView result;
		Qulp qulp;

		qulp = this.qulpService.findOne(qulpId);

		try {
			this.qulpService.delete(qulp);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("misc/error");
		}

		return result;

	}

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final Qulp q) {
		ModelAndView result;
		result = this.createEditModelAndView(q, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Qulp q, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("qulp/edit");
		Collection<Conference> conferences = this.conferenceService.findAllNoDraft();
		result.addObject("conferences", conferences);
		result.addObject("qulp", q);
		result.addObject("message", messageCode);

		return result;
	}

}
