
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConfigurationService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	//AQUI VAN LOS SERVISIOS QUE  NOS HAGA FALTA
	//listar, crear y borrar

	@Autowired
	CategoryService	categoryService;
	@Autowired
	private ConfigurationService	configService;	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allCategories() {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		final String requestURI = "category/list.do";
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", requestURI);
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		Category category;
		category = this.categoryService.create();
		result = this.createEditModelAndView(category);
		return result;
	}

	//Edit 

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findCategoryById(categoryId);
		Assert.notNull(category);
		result = this.createEditModelAndView(category);

		return result;

	}

	//Delete

	// Deleting
	@RequestMapping(value = "/remove", method = RequestMethod.GET, params = {
		"id"
	})
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		final Collection<Category> categories;
			try {
			this.categoryService.delete(this.categoryService.findCategoryById(id));
			categories = this.categoryService.findAll();
			result = new ModelAndView("category/list");
			result.addObject("categories", categories);
			result.addObject("configuration", configService.findConfiguration());
			} catch (final Throwable oops) {
				result = new ModelAndView();;
				oops.printStackTrace();
			}
		return result;
	}

	//Save

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(category);
			result.addObject("bind", binding);
		} else
			try {

				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
				result.addObject("configuration", configService.findConfiguration());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "finder.commit.error");
				oops.printStackTrace();
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(category);
			result.addObject("bind", binding);
		} else
			try {

				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
						result.addObject("configuration", configService.findConfiguration());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "finder.commit.error");
				oops.printStackTrace();
			}
		return result;
	}
	//Ancillary metods

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;
		result = this.createEditModelAndView(category, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView result;
		final Collection<Category> jeje = this.categoryService.findAll();
		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("parentCategories", jeje);
		result.addObject("message", messageCode);
		result.addObject("configuration", configService.findConfiguration());
		return result;

	}

}
