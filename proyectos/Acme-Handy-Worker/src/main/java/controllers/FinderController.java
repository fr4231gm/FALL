
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConfigurationService;
import services.FinderService;
import services.WarrantyService;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/finder/handyworker")
public class FinderController extends AbstractController {

	//AQUI VAN LOS SERVISIOS QUE  NOS HAGA FALTA
	@Autowired
	private FinderService	finderService;

	@Autowired
	private WarrantyService	warrantyService;

	@Autowired
	private CategoryService			categoryService;
	
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ConfigurationService	configService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allFixUpTasks() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		final Finder finder = this.finderService.findOneByPrincipal();
		Date lastUpdate = finder.getLastUpdate();
		Date endTime= new Date();
		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		Integer period = (int) (lastUpdate.getTime() - endTime.getTime()) / MILLI_TO_HOUR;
		if(period>this.configurationService.findConfiguration().getFinderLifeSpan()){
			try {
				this.finderService.saveFinder(finder);
			}catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
				oops.printStackTrace();
			}
		}
		fixUpTasks = finder.getFixUpTasks();
		final String requestURI = "fixUpTask/list.do";
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", requestURI);
		return result;
	}
	//	Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findOneByPrincipal();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;

	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

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
	//Ancillary metods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;
		Collection<Warranty> warranties;
		Collection<Category> categories;

		warranties = this.warrantyService.findAll();
		categories = this.categoryService.findAll();

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);
		result.addObject("configuration", configService.findConfiguration());
		result.addObject("message", messageCode);

		return result;

	}

}
