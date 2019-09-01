
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.FinderService;
import services.RookieService;
import domain.Finder;
import domain.Position;

@Controller
@RequestMapping("/finder/rookie")
public class FinderController extends AbstractController {

	//AQUI VAN LOS SERVISIOS QUE  NOS HAGA FALTA
	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private RookieService			rookieService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allPositions() {
		ModelAndView result;
		final Collection<Position> position;
		final Finder finder = this.rookieService.findByPrincipal().getFinder();
		if (finder.getId() != 0)
			finder.setVersion(this.rookieService.findByPrincipal().getFinder().getVersion());
		final Date lastUpdate = finder.getLastUpdate();
		final Date endTime = new Date();
		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		final Integer period = (int) (lastUpdate.getTime() - endTime.getTime()) / MILLI_TO_HOUR;
		if (period > this.configurationService.findConfiguration().getFinderLifeSpan())
			try {
				this.finderService.saveFinder(finder);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
				oops.printStackTrace();
			}
		position = finder.getPositions();
		final String requestURI = "finder/rookie/list.do";
		result = new ModelAndView("position/list");
		result.addObject("positions", position);
		result.addObject("requestURI", requestURI);
		result.addObject("finderCome", true);
		return result;
	}
	//	Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		finder = this.rookieService.findByPrincipal().getFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (finder.getId() != 0)
			finder.setVersion(this.rookieService.findByPrincipal().getFinder().getVersion());
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(finder);
			result.addObject("bind", binding);
		} else
			try {
				this.finderService.saveFinder(finder);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
				oops.printStackTrace();
			}
		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (finder.getId() != 0)
			finder.setVersion(this.rookieService.findByPrincipal().getFinder().getVersion());

		final Date lastUpdate = finder.getLastUpdate();
		final Date endTime = new Date();
		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		Integer period = 0;
		if (lastUpdate != null)
			period = (int) (lastUpdate.getTime() - endTime.getTime()) / MILLI_TO_HOUR;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(finder);
			result.addObject("bind", binding);
		} else
			try {
				if (finder.getVersion() == 0)
					this.finderService.saveFinder(finder);
				else if (this.finderService.filterFinder(finder)) {
					if (period > this.configurationService.findConfiguration().getFinderLifeSpan())
						this.finderService.saveFinder(finder);
				} else
					this.finderService.saveFinder(finder);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
				oops.printStackTrace();
			}
		return result;
	}
	//Ancillary metods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("configuration", this.configurationService.findConfiguration());
		result.addObject("message", messageCode);

		return result;

	}

}
