/*
 * MemberController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import domain.Member;
import forms.MemberForm;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	// Singletons
	@Autowired
	private MemberService	memberService;
	

	// Constructors -----------------------------------------------------------

	public MemberController() {
		super();
	}

	// List the members of a brotherhood
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	ModelAndView listByBrotherhood(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<Member> members;

		members = this.memberService.findMembersByBrotherhood(brotherhoodId);
		result = new ModelAndView("member/list");
		result.addObject("members", members);
		result.addObject("requestURI", "member/list.do?brotherhoodId=" + brotherhoodId);

		return result;
	}

	// DISPLAY

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"memberId"
	})
	public ModelAndView display(final int memberId) {
		ModelAndView res;

		Member member;

		member = this.memberService.findOne(memberId);

		res = new ModelAndView("member/display");
		res.addObject("member", member);
		return res;
	}

	// Create A New Member
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register() {
		ModelAndView result;
		MemberForm memberForm;

		memberForm = new MemberForm();
		memberForm.setCheckTerms(false);

		result = new ModelAndView("member/register");
		result.addObject("memberForm", memberForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	ModelAndView save(final MemberForm memberForm, final BindingResult binding) {
		// Initialize Variables
		ModelAndView result;
		Member member;

		// Create the member object from the memberForm
		member = this.memberService.reconstruct(memberForm, binding);

		// If the form has errors prints it
		if (binding.hasErrors())
			result = new ModelAndView("member/register");
		else
			// If the form does not have errors, try to save it
			try {
				this.memberService.save(member);
				result = new ModelAndView("redirect:../");
				result.addObject("message", "actor.register.success");
				result.addObject("name", member.getName());
			} catch (final Throwable oops) {
				result = new ModelAndView("member/register");
				result.addObject("message", "actor.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	ModelAndView edit() {
		ModelAndView result;
		Member member;
		result = new ModelAndView("member/edit");

		try {
			member = this.memberService.findOneTrimmedByPrincipal();
			result.addObject("member", member);
			
		} catch (final Throwable oops) {
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Member member, final BindingResult binding) {
		ModelAndView result;
		Member toSave;
		SimpleDateFormat formatter;
		String moment;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		toSave = this.memberService.reconstruct(member, binding);

		if (binding.hasErrors())
			result = new ModelAndView("member/edit");
		else
			try {
				this.memberService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("moment", moment);
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = new ModelAndView("member/edit");
				result.addObject("message", "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	ModelAndView delete() {
		ModelAndView result;
		String exportedData;

		try {
			exportedData = this.memberService.deleteUserAccount();
			result = new ModelAndView("redirect:/j_spring_security_logout");
			result.addObject("exitCode", "actor.delete.success");
			result.addObject("exportedData", exportedData);

		} catch (final Throwable oops) {
			result = new ModelAndView("member/edit");
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "export")
	ModelAndView exportData() {
		ModelAndView result;
		String exportedData;
		
		try {
			exportedData = this.memberService.exportData();
			result = new ModelAndView("welcome/index");
			result.addObject("exportedData", exportedData);

		} catch (Throwable oops) {
			
			result = new ModelAndView();
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}
}
