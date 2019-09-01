/*
 * ActorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.MemberService;
import services.MessageService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Member;
import domain.Message;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Singletons
	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private MessageService			messageService;


	// Constructors -----------------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	//Spammer 
	@RequestMapping(value = "/spammers", method = RequestMethod.GET)
	public ModelAndView spammer() {
		Collection<Member> members;
		Collection<Brotherhood> brotherhoods;

		ModelAndView result;
		members = this.memberService.findAll();
		brotherhoods = this.brotherhoodService.findAll();
		Collection<Message> messages;
		Integer cantidad, spamming;
		for (final Member m : members) {
			spamming = 0;
			messages = this.messageService.findSended(m.getId());
			cantidad = messages.size();
			for (final Message m2 : messages)
				if (m2.getIsSpam())
					spamming++;
			if (cantidad != 0)
				if ((spamming / cantidad) >= 0.1)
					m.setSpammer(true);

		}

		for (final Brotherhood b : brotherhoods) {
			spamming = 0;
			messages = this.messageService.findSended(b.getId());
			cantidad = messages.size();
			for (final Message m2 : messages)
				if (m2.getIsSpam())
					spamming++;

			if (cantidad != 0)
				if ((spamming / cantidad) >= 0.1)
					b.setSpammer(true);

		}

		result = new ModelAndView("administrator/choose");

		return result;
	}

	//Polarity
	@RequestMapping(value = "/polarity", method = RequestMethod.GET)
	public ModelAndView polarity() {
		Collection<Member> members;
		Collection<Brotherhood> brotherhoods;
		Double polarity;
		ModelAndView result;
		members = this.memberService.findAll();
		brotherhoods = this.brotherhoodService.findAll();
		for (final Member m : members) {
			polarity = this.administratorService.polarity(m);
			if (!polarity.isNaN())
				m.setPolarity(polarity);

		}

		for (final Brotherhood b : brotherhoods) {
			polarity = this.administratorService.polarity(b);
			if (!polarity.isNaN())
				b.setPolarity(polarity);

		}

		result = new ModelAndView("administrator/choose");

		return result;
	}

	//Intermediario
	@RequestMapping(value = "/choose", method = RequestMethod.GET)
	public ModelAndView choose() {

		ModelAndView result;
		result = new ModelAndView("administrator/choose");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = "election")
	public ModelAndView list(@RequestParam final int election) {
		ModelAndView result;

		if (election == 0) {
			Collection<Brotherhood> brotherhood;
			brotherhood = this.brotherhoodService.findAll();
			Assert.notNull(brotherhood);
			result = new ModelAndView("actor/listBanBrotherhood");

			result.addObject("actor", brotherhood);
		} else {
			Collection<Member> member;
			member = this.memberService.findAll();
			Assert.notNull(member);
			result = new ModelAndView("actor/listBanMember");
			result.addObject("actor", member);
		}

		result.addObject("id", this.actorService.findByPrincipal().getId());

		result.addObject("requestURI", "actor/administrator/list.do");
		result.addObject("configuration", this.configService.findConfiguration());
		return result;
	}

	//Ban  unban LA MADRE DE LOS WORK AROUNDS
	@RequestMapping(value = "/banBrotherhood", method = RequestMethod.GET, params = "brotherhoodId")
	public ModelAndView banBrotherhood(@RequestParam final int brotherhoodId) {

		final ModelAndView result;
		final Brotherhood b;
		b = this.brotherhoodService.findOne(brotherhoodId);
		this.administratorService.changeBan(b.getUserAccount());

		result = new ModelAndView("redirect:list.do?election=0");

		return result;
	}
	@RequestMapping(value = "/banMember", method = RequestMethod.GET, params = "memberId")
	public ModelAndView banMember(@RequestParam final int memberId) {
		final ModelAndView result;
		final Member m;
		m = this.memberService.findOne(memberId);
		this.administratorService.changeBan(m.getUserAccount());
		result = new ModelAndView("redirect:list.do?election=1");
		return result;
	}

	//	protected ModelAndView createEditModelAndView(final ActorForm actor) {
	//		final ModelAndView result = this.createEditModelAndView(actor, null);
	//		return result;
	//	}
	//
	//	private ModelAndView createEditModelAndView(final ActorForm actor, final String messagecode) {
	//		ModelAndView result;
	//		result = new ModelAndView("actor/edit");
	//		result.addObject("actorForm", actor);
	//		result.addObject("message", messagecode);
	//		result.addObject("configuration", this.configService.findConfiguration());
	//		return result;
	//	}
}
