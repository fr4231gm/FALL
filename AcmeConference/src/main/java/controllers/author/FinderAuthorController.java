
package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.CategoryService;
import services.ConfigurationService;
import services.FinderService;
import controllers.AbstractController;
import domain.Conference;
import domain.Finder;

@Controller
@RequestMapping("/finder/author")
public class FinderAuthorController extends AbstractController {

	//AQUI VAN LOS SERVISIOS QUE  NOS HAGA FALTA
	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private FinderService			finderService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allConferences() {
		ModelAndView result;
		final Collection<Conference> conference;
		final Finder finder = this.authorService.findByPrincipal().getFinder();
		if (finder.getId() != 0)
			finder.setVersion(this.authorService.findByPrincipal().getFinder().getVersion());
		try {
			this.finderService.saveFinder(finder);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "finder.commit.error");
			oops.printStackTrace();
		}
		conference = finder.getConferences();
		final String requestURI = "finder/author/list.do";
		result = new ModelAndView("conference/list");
		result.addObject("conferences", conference);
		result.addObject("requestURI", requestURI);
		return result;
	}
	//	Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		finder = this.authorService.findByPrincipal().getFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;

	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (finder.getId() != 0)
			finder.setVersion(this.authorService.findByPrincipal().getFinder().getVersion());
		if (finder.getStartDate() != null & finder.getEndDate() != null)
			if (!finder.getStartDate().before(finder.getEndDate())) {
				binding.rejectValue("startDate", "date.end.date.future");
				result = this.createEditModelAndView(finder);
				result.addObject("bind", binding);
			}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(finder);
			result.addObject("bind", binding);
		} else {
			//			try {
			this.finderService.saveFinder(finder);
			result = new ModelAndView("redirect:list.do");
		}
		//			} catch (final Throwable oops) {
		//				result = this.createEditModelAndView(finder, "finder.commit.error");
		//			}
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
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("finder", finder);
		result.addObject("configuration", this.configurationService.findConfiguration());
		result.addObject("message", messageCode);

		return result;

	}
}
