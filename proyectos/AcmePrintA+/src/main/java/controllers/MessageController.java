
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
import services.BoxService;
import services.ConfigurationService;
import services.MessageService;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Message;
import forms.MoveMessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services
	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private BoxService				boxService;


	// Constructors
	public MessageController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"id"
	})
	public ModelAndView display(@RequestParam final int id) {
		ModelAndView result;
		Message m;
		final Actor principal = this.actorService.findByPrincipal();
		try {
			Assert.isTrue(this.messageService.findOne(id).getRecipients().contains(principal) || this.messageService.findOne(id).getSender() == principal);
			try {
				m = this.messageService.findOne(id);
				result = new ModelAndView("message/display");
				result.addObject("m", m);
				result.addObject("cancelURI", "box/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
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
		Message m;
		m = this.messageService.create();
		result = this.createEditModelAndView(m);
		final String[] priorities = {"HIGH", "NEUTRAL", "LOW"};
		result.addObject("priorities", priorities);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid @ModelAttribute("m") final Message me, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(me);
			result.addObject("bind", binding);

		} else
			try {

				this.messageService.save(me);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
			}

		return result;
	}
	
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam int messageId,  int boxId) {
		ModelAndView result;
		MoveMessageForm moveMessageForm;
		moveMessageForm= new MoveMessageForm();
		try {
			moveMessageForm.setMessage(this.messageService.findOne(messageId));
			moveMessageForm.setCurrentbox(this.boxService.findOne(boxId));
			moveMessageForm.setIsCopy(false);
			result = new ModelAndView("message/move");
			result.addObject("moveMessageForm", moveMessageForm);
			result.addObject("boxes", this.boxService.findBoxesByPrincipal());
			
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final MoveMessageForm moveMessageForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("message/move");
			result.addObject("moveMessageForm", moveMessageForm);
			result.addObject("boxes", this.boxService.findBoxesByPrincipal());
		} else
			try {
				Message message = moveMessageForm.getMessage();
				Box box = moveMessageForm.getBox();
				if(moveMessageForm.getIsCopy()){
					Collection <Box> boxes = message.getBoxes();
					Collection<Message> messages = box.getMessages();
					boxes.add(box);
					messages.add(message);
					box.setMessages(messages);
					message.setBoxes(boxes);
					this.messageService.save(message);
					this.boxService.save(box);
				} else {
					this.messageService.move(message, moveMessageForm.getCurrentbox(), moveMessageForm.getBox());
				}
				result = new ModelAndView("redirect:/box/list.do?boxId=" + moveMessageForm.getBox().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
			}
		return result;
	}
	

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		ModelAndView result;
		Message m;
		m = this.messageService.create();
		m.setRecipients(this.actorService.findAll());
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.createEditModelAndView(m);
		final String[] priorities = {"HIGH", "NEUTRAL", "LOW"};
		result.addObject("priorities", priorities);
		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView broadcast2(@Valid @ModelAttribute("m") final Message me, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(me);
			result.addObject("bind", binding);
		} else
			try {
				this.messageService.save(me);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
			}

		return result;
	}

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public ModelAndView notification() {
		ModelAndView result;
		Message m;
		m = this.messageService.create();
		m.setRecipients(this.actorService.findAll());
		m.setSubject("Security Violation");
		m.setBody("We have found a security breach, we are very sorry, we are working to solve it");
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.createEditModelAndView(m);
		final String[] priorities = {"HIGH", "NEUTRAL", "LOW"};
		result.addObject("priorities", priorities);
		return result;
	}

	@RequestMapping(value = "/notification", method = RequestMethod.POST, params = "save")
	public ModelAndView otification2(@Valid @ModelAttribute("m") final Message me, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(me);
			result.addObject("bind", binding);

		} else
			try {
				this.messageService.saveNotification(me);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
			}

		return result;
	}

	// Deleting
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int id, final int boxId) {
		ModelAndView result;
		try {
			this.messageService.delete(this.messageService.findOne(id), boxId);
			result = new ModelAndView("redirect:/box/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/box/list.do");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m) {
		final ModelAndView result = this.createEditModelAndView(m, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Message m, final String messagecode) {
		ModelAndView result;
		final Collection<Actor> recipients = this.actorService.findAll();
		result = new ModelAndView("message/edit");
		result.addObject("m", m);
		result.addObject("message", messagecode);
		result.addObject("recipients", recipients);
		result.addObject("configuration", this.configService.findConfiguration());
		final String[] priorities = {"HIGH", "NEUTRAL", "LOW"};
		result.addObject("priorities", priorities);
		return result;
	}
}
