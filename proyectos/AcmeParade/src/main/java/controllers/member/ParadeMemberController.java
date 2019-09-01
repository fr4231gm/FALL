/*
 * MemberController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Member;
import domain.Parade;

@Controller
@RequestMapping("/parade/member")
public class ParadeMemberController extends AbstractController {

	// Singletons
	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private MemberService	memberService;


	// Constructors -----------------------------------------------------------

	public ParadeMemberController() {
		super();
	}

	// List the parade of a brotherhood
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;
		Collection<Parade> parades;
		final Member principal = this.memberService.findByPrincipal();

		parades = this.paradeService.findParadeRequestablesByMemberId(principal.getId());

		res = new ModelAndView("parade/list");
		res.addObject("parades", parades);
		res.addObject("requestURI", "parade/member/list.do");
		res.addObject("permiso", true);
		return res;
	}

}
