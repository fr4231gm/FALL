
package controllers;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;
import forms.CategoryForm;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	@Autowired
	CategoryService	categoryService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allCategories() {
		ModelAndView result;
		Collection<Category> categories;
		String language;
		
		try{
			final Locale locale = LocaleContextHolder.getLocale();
			language = locale.getLanguage();
		} catch (Throwable oops){
			language = "en";
		}
		categories = this.categoryService.findAll();
		final String requestURI = "category/list.do";
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", requestURI);
		result.addObject("language", language);
		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		CategoryForm categoryForm;
		categoryForm = new CategoryForm();
		result = this.createEditModelAndView(categoryForm);
		return result;
	}

	// Edit 
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;
		CategoryForm categoryForm;

		category = this.categoryService.findCategoryById(categoryId);
		Assert.notNull(category);
		categoryForm = this.categoryService.construct(category);
		result = this.createEditModelAndView(categoryForm);

		return result;
	}

	// Delete
	@RequestMapping(value = "/remove", method = RequestMethod.GET, params = { "id" })
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		Collection<Category> categories;
		try {
			this.categoryService.delete(this.categoryService.findCategoryById(id));
			categories = this.categoryService.findAll();
			result = new ModelAndView("category/list");
			result.addObject("categories", categories);
		} catch (final Throwable oops) {
			result = new ModelAndView();;
			result = new ModelAndView("category/list");
			categories = this.categoryService.findAll();
			result.addObject("categories", categories);
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CategoryForm category, final BindingResult binding) {
		ModelAndView result;
		try {
			Category toSave = this.categoryService.reconstruct(category);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(category);
				result.addObject("bind", binding);
			} else {
				
				this.categoryService.save(toSave);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "actor.commit.error");
				
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final CategoryForm category, final BindingResult binding) {
		ModelAndView result;
		try {
			Category toSave = this.categoryService.reconstruct(category);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(category);
				result.addObject("bind", binding);
			} else {
				this.categoryService.save(toSave);
				result = new ModelAndView("redirect:list.do");
			}
		}catch (final Throwable oops) {
			result = this.createEditModelAndView(category, "actor.commit.error");
		}
		return result;
	}


	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm) {
		ModelAndView result;
		result = this.createEditModelAndView(categoryForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm, final String messageCode) {
		ModelAndView result;
		final Collection<Category> parentCategories = this.categoryService.findAll();
		String language;
		
		try {
			 if(categoryForm.getId()!= 0){
				parentCategories.remove(this.categoryService.findCategoryById(categoryForm.getId()));
			}
		} catch (Throwable oops){ }
		try{
			final Locale locale = LocaleContextHolder.getLocale();
			language = locale.getLanguage();
		} catch (Throwable oops){
			language = "en";
		}
		
		result = new ModelAndView("category/edit");
		result.addObject("categoryForm", categoryForm);
		result.addObject("categories", parentCategories);
		result.addObject("message", messageCode);
		result.addObject("language", language);
		return result;

	}

}
