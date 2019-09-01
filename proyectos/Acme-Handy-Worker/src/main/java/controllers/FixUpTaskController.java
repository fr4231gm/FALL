/*
 * FixUpTaskController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;

import domain.Actor;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask")
public class FixUpTaskController extends AbstractController {

	@Autowired
	FixUpTaskService fixUpTaskService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	WarrantyService warrantyService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ActorService actorService;

	@Autowired
	ConfigurationService	configService;
	// Constructors -----------------------------------------------------------

	public FixUpTaskController() {
		super();
	}

	// list All Fix Up Tasks
	// ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allFixUpTasks() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		Actor a = this.actorService.findByPrincipal();
		fixUpTasks = this.fixUpTaskService.findAll();
		String requestURI = "fixUpTask/list.do";
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("principal", a);
		result.addObject("requestURI", requestURI);
		return result;
	}
	
	@RequestMapping(value = "/listbyc", method = RequestMethod.GET)
	public ModelAndView CustomerFixUpTasks(@RequestParam int customerId) {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		Customer c = this.customerService.findById(customerId);
		fixUpTasks = this.fixUpTaskService.findFixUpTaskPerCustomer(c);
		String requestURI = "fixUpTask/listbyc.do";
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", requestURI);
		return result;
	}

	// Display A Fix-Up Task
	// ---------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showFixUpTask(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		result = new ModelAndView("fixUpTask/show");
		result.addObject("fixUpTask", fixUpTask);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createFixUpTask() {
		ModelAndView result;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.create();
		result = createEditModelAndView(fixUpTask);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editFixUpTask(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		result = createEditModelAndView(fixUpTask);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FixUpTask fixUpTask, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(fixUpTask);
			result.addObject("bind",binding);
		} else {

				fixUpTaskService.saveFixUpTask(fixUpTask);
				result = new ModelAndView("redirect:list.do");
			
		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid FixUpTask fixUpTask, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(fixUpTask);
			result.addObject("bind",binding);
		} else {
			try {
				fixUpTaskService.saveFixUpTask(fixUpTask);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(fixUpTask,
						"fixUpTask.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(FixUpTask fixUpTask) {
		ModelAndView result = createEditModelAndView(fixUpTask, null);
		return result;
	}

	private ModelAndView createEditModelAndView(FixUpTask fixUpTask,
			String messagecode) {
		ModelAndView result;
		Collection<Category> categories = this.categoryService.findAll();
		Collection<Warranty> warranties = this.warrantyService.findAllFinals();
		result = new ModelAndView("fixUpTask/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("message", messagecode);
		result.addObject("lan", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}

}
