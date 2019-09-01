package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import services.MessageService;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services
	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private ConfigurationService configService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public MessageController() {
		super();
	}

	// Display
	@RequestMapping(value = "/show", method = RequestMethod.GET, params = { "id" })
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;
		Message m;
		final Actor principal = this.actorService.findByPrincipal();
		try {
			Assert.isTrue(this.messageService.findOne(id).getRecipient() == principal
					|| this.messageService.findOne(id).getSender() == principal);
			try {
				m = this.messageService.findOne(id);
				result = new ModelAndView("message/show");
				result.addObject("m", m);
				result.addObject("cancelURI", "message/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/message/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;
	}

	// Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageForm m;
		m = new MessageForm();
		result = this.createEditModelAndView(m);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid @ModelAttribute("m") final MessageForm me,
			final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(me);
			result.addObject("bind", binding);
		} else
			try {
				this.messageService.save(me);
				result = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/message/list.do");
			}

		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		ModelAndView result;
		MessageForm m;
		m = new MessageForm();
		m.setRecipients(this.actorService.findAll());
		m.setTags("SYSTEM");
		final Administrator principal = this.administratorService
				.findByPrincipal();
		Assert.notNull(principal);
		result = this.createEditModelAndView(m);
		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView broadcast2(
			@Valid @ModelAttribute("m") final MessageForm me,
			final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(me);
			result.addObject("bind", binding);
		} else
			try {
				this.messageService.save(me);
				result = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/message/list.do");
			}

		return result;
	}

	// Deleting
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int id) {
		ModelAndView result;
		try {
			this.messageService.delete(this.messageService.findOne(id));
			result = new ModelAndView("redirect:/message/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/message/list.do");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor principal = this.actorService.findByPrincipal();
		Collection<Message> sended;
		Collection<Message> received;
		sended = this.messageService.findSended(principal.getId());
		received = this.messageService.findReceived(principal.getId());
		result = new ModelAndView("message/list");
		result.addObject("received", received);
		result.addObject("sended", sended);
		result.addObject("requestURI", "message/list.do");
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm m) {
		final ModelAndView result = this.createEditModelAndView(m, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final MessageForm m,
			final String messagecode) {
		ModelAndView result;
		final Collection<Actor> recipients = this.actorService.findAll();
		result = new ModelAndView("message/edit");
		result.addObject("m", m);
		result.addObject("message", messagecode);
		result.addObject("recipients", recipients);
		result.addObject("configuration",
				this.configService.findConfiguration());
		return result;
	}
}
