/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.brotherhood;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EnrolmentService;
import services.MemberService;
import controllers.AbstractController;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping("/enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public EnrolmentBrotherhoodController() {
		super();
	}


	// Service
	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private MemberService		memberService;


	// List of all enrolments that belongs to the logged brotherhood with position assigned		

	@RequestMapping("/list/assigned")
	public ModelAndView listAssignedEnrolments() {
		//Initialize variables
		ModelAndView result;
		Collection<Enrolment> enrolmentsList;
		Locale locale;

		locale = LocaleContextHolder.getLocale();

		//Add to the brotherhood enrolments list the ones with position
		enrolmentsList = this.enrolmentService.findEnrolmentsWhithPositionByPrincipal();

		//Create the Model And View
		result = new ModelAndView("enrolment/brotherhood/list/assigned");

		//Inject the elements in the view
		result.addObject("enrolments", enrolmentsList);
		result.addObject("requestURI", "/enrolment/brotherhood/list/assigned.do");
		result.addObject("language", locale);
		return result;
	}

	// List of all enrolments that belongs to the logged brotherhood without position assigned
	@RequestMapping("/list/unassigned")
	public ModelAndView listUnAssignedEnrolments() {
		//Initialize variables
		ModelAndView result;
		Collection<Enrolment> enrolmentsList;
		Locale locale;

		locale = LocaleContextHolder.getLocale();

		//Add to the brotherhood enrolments list the ones without position
		enrolmentsList = this.enrolmentService.findEnrolmentsWhithoutPositionByPrincipal();

		//Create the Model And View
		result = new ModelAndView("enrolment/brotherhood/list/unassigned");

		//Inject the elements in the view
		result.addObject("enrolments", enrolmentsList);
		result.addObject("requestURI", "/enrolment/brotherhood/list/unassigned.do");
		result.addObject("language", locale);
		return result;
	}

	// DropOut a member
	@RequestMapping(value = "/dropOut", method = RequestMethod.GET, params = {
		"enrolmentId"
	})
	public ModelAndView leave(final int enrolmentId) {
		//Initialize variables
		ModelAndView result;

		//Create the ModelAndView
		result = new ModelAndView("redirect:/enrolment/brotherhood/list/assigned.do");

		//Try To Leave  the brotherhood
		try {
			this.enrolmentService.dropOut(enrolmentId);
		} catch (final Throwable oops) {
			result.addObject("message", "enrolment.commit.error");
		}

		return result;
	}

	// DISPLAY

	/*
	 * @RequestMapping(value = "/display", method = RequestMethod.GET, params = { "enrolmentId" })
	 * public ModelAndView display(final int enrolmentId) {
	 * ModelAndView res;
	 * 
	 * Member member;
	 * 
	 * member = this.enrolmentService.findOne(enrolmentId).getMember();
	 * 
	 * res = new ModelAndView("enrolment/brotherhood/display");
	 * res.addObject("member", member);
	 * return res;
	 * }
	 */

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"memberId"
	})
	public ModelAndView display(final int memberId) {
		ModelAndView res;

		Member member;

		member = this.memberService.findOne(memberId);

		res = new ModelAndView("member/brotherhood/display");
		res.addObject("member", member);
		return res;
	}
}
