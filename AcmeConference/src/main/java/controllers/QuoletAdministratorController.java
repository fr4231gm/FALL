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
import services.QuoletService;
import domain.Administrator;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController extends AbstractController {

	@Autowired
	private QuoletService quoletService;

	@Autowired
	private AdministratorService administratorService;

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Quolet quolet = this.quoletService.create();

		res = this.createEditModelAndView(quolet);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Quolet quolet,
			final BindingResult binding) {
		ModelAndView res;

		if (quolet.getIsDraft() == null)
			quolet.setIsDraft(false);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(quolet);
			res.addObject("binding", binding);
		} else
			try {
				this.quoletService.save(quolet);
				res = new ModelAndView("redirect:list.do");
			}

			catch (final Throwable oops) {
				res = this
						.createEditModelAndView(quolet, "quolet.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int quoletId) {
		ModelAndView res;
		final Quolet quolet;
		Administrator principal;
		quolet = this.quoletService.findOne(quoletId);
		principal = this.administratorService.findByPrincipal();

		if (quolet.getAdministrator().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = new ModelAndView("quolet/edit");
				res.addObject("quolet", quolet);

			} catch (final Throwable oops) {

				res = new ModelAndView("quolet/edit");
				res.addObject("quolet", quolet);
				res.addObject("message", "quolet.commit.error");
			}
		return res;
	}

	// Save edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Quolet quolet,
			final BindingResult binding) {
		ModelAndView res;

		if (quolet.getIsDraft() == null)
			quolet.setIsDraft(false);

		if (binding.hasErrors()) {
			res = new ModelAndView("quolet/edit");
			res.addObject("quolet", quolet);
		} else
			try {

				this.quoletService.save(quolet);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this
						.createEditModelAndView(quolet, "quolet.commit.error");
			}
		return res;
	}

	// List----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		final Collection<Quolet> quolets = this.quoletService
				.findByAdministrator(this.administratorService
						.findByPrincipal().getId());
		final Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -1);
		final Date oneMonth = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		final Date twoMonths = cal.getTime();
		res = new ModelAndView("quolet/list");
		res.addObject("oneMonth", oneMonth);
		res.addObject("twoMonths", twoMonths);
		res.addObject("quolets", quolets);
		res.addObject("requestURI", "quolet/administrator/list.do");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int quoletId) {

		ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);

		try {
			this.quoletService.delete(quolet);
			result = new ModelAndView("welcome/index");
		} catch (final Throwable oops) {
			result = new ModelAndView("misc/error");
		}

		return result;

	}

	// Ancillary metods
	protected ModelAndView createEditModelAndView(final Quolet q) {
		ModelAndView result;
		result = this.createEditModelAndView(q, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Quolet q,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", q);
		result.addObject("message", messageCode);

		return result;
	}

}
