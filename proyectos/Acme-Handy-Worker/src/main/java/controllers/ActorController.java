package controllers;

import java.util.Collection;

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

import services.ActorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import forms.ActorForm;


@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ConfigurationService	configService;
	
	// Constructors
	public ActorController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/showbyprincipal", method = RequestMethod.GET)
	public ModelAndView show() {
		Actor a = this.actorService.findByPrincipal();
		ModelAndView result;
		
		try {
			result = new ModelAndView("actor/show");
			result.addObject("actor", a);
			result.addObject("configuration", configService.findConfiguration());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
			result.addObject("configuration", configService.findConfiguration());
		}
		return result;
	}


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show2(@RequestParam final int id) {
		Actor a = this.actorService.findOne(id);
		ModelAndView result;
		try {
			
			result = new ModelAndView("actor/show");
			result.addObject("actor", a);
			result.addObject("configuration", configService.findConfiguration());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
			result.addObject("configuration", configService.findConfiguration());
		}
		return result;
	}
	
	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Actor> actors;
		actors = this.actorService.findAll();
		Assert.notNull(actors);

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestURI", "actor/list.do");
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}
	
	// Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createBox() {
		ModelAndView result;
		ActorForm actorForm = new ActorForm();
		result = this.createEditModelAndView(actorForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				actorService.save(actorForm);
				result = new ModelAndView("security/login.do");
				result.addObject("configuration", configService.findConfiguration());
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
				result.addObject("configuration", configService.findConfiguration());
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm actor) {
		ModelAndView result = createEditModelAndView(actor, null);
		return result;
	}

	private ModelAndView createEditModelAndView(ActorForm actor, String messagecode) {
		ModelAndView result;
		result = new ModelAndView("actor/edit");
		result.addObject("actorForm", actor);
		result.addObject("message", messagecode);
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}
}