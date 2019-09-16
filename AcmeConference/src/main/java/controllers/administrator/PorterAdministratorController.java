package controllers.administrator;

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
import services.PorterService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
import domain.Porter;

@Controller
@RequestMapping("/porter/administrator")
public class PorterAdministratorController extends AbstractController {

	@Autowired
	private PorterService porterService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConferenceService conferenceService;

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Porter porter = this.porterService.create();

		res = this.createEditModelAndView(porter);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Porter porter,
			final BindingResult binding) {
		ModelAndView res;

		if (porter.getIsDraft() == null)
			porter.setIsDraft(false);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(porter);
			res.addObject("binding", binding);
		} else
			try {
				this.porterService.save(porter);
				res = new ModelAndView("redirect:list.do");
			}

			catch (final Throwable oops) {
				res = this
						.createEditModelAndView(porter, "porter.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int porterId) {
		ModelAndView res;
		final Porter porter;
		Administrator principal;
		porter = this.porterService.findOne(porterId);
		principal = this.administratorService.findByPrincipal();

		if (porter.getIsDraft().equals(false)){
			res = new ModelAndView("security/hacking");
		} else if (porter.getAdministrator().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = new ModelAndView("porter/edit");
				res.addObject("porter", porter);

			} catch (final Throwable oops) {

				res = new ModelAndView("porter/edit");
				res.addObject("porter", porter);
				res.addObject("message", "porter.commit.error");
			}

		res.addObject("conferences", this.conferenceService.findAllNoDraft());

		return res;
	}

	// Save edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Porter porter,
			final BindingResult binding) {
		ModelAndView res;

		if (porter.getIsDraft() == null)
			porter.setIsDraft(false);

		if (binding.hasErrors()) {
			res = new ModelAndView("porter/edit");
			res.addObject("porter", porter);
		} else
			try {

				this.porterService.save(porter);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this
						.createEditModelAndView(porter, "porter.commit.error");
			}
		return res;
	}

	// List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		Collection<Porter> porters = this.porterService
				.findByAdministrator(this.administratorService
						.findByPrincipal().getId());
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -1);
		final Date oneMonth = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		final Date twoMonths = cal.getTime();
		res = new ModelAndView("porter/list");
		res.addObject("permiso", true);
		res.addObject("oneMonth", oneMonth);
		res.addObject("twoMonths", twoMonths);
		res.addObject("porters", porters);
		res.addObject("requestURI", "porter/administrator/list.do");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int porterId) {

		ModelAndView result;
		Porter porter;

		porter = this.porterService.findOne(porterId);

		try {
			this.porterService.delete(porter);
			result = new ModelAndView("redirect:/porter/administrator/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("misc/error");
		}

		return result;

	}

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final Porter q) {
		ModelAndView result;
		result = this.createEditModelAndView(q, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Porter q,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("porter/edit");
		Collection<Conference> conferences = this.conferenceService.findAllNoDraft();
		result.addObject("conferences", conferences);
		result.addObject("porter", q);
		result.addObject("message", messageCode);

		return result;
	}

}
