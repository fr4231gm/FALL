package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;
import domain.Message;


@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ConfigurationService	configService;
	

	// Constructors
	public MessageController() {
		super();
	}

	// Display
	@RequestMapping(value = "/show", method = RequestMethod.GET, params = { "id" })
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;
		Message m;
		try {
			m = this.messageService.findOne(id);
			result = new ModelAndView("message/show");
			result.addObject("m", m);
			result.addObject("cancelURI", "box/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		return result;
	}

	// Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message m;
		m = this.messageService.create();
		result = createEditModelAndView(m);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid @ModelAttribute("m") Message me, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(me);
			result.addObject("bind", binding);
		} else {
			try{
				messageService.save(me);
				result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		}
		return result;
	}
	
	// Deleting
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int id, final int boxId) {
		ModelAndView result;
		try {
			this.messageService.delete(this.messageService.findOne(id), boxId);
			result = new ModelAndView();
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Message m) {
		ModelAndView result = createEditModelAndView(m, null);
		return result;
	}

	private ModelAndView createEditModelAndView(Message m, String messagecode) {
		ModelAndView result;
		Collection<Actor> recipients = this.actorService.findAll();
		result = new ModelAndView("message/edit");
		result.addObject("m", m);
		result.addObject("message", messagecode);
		result.addObject("recipients", recipients);
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}
}