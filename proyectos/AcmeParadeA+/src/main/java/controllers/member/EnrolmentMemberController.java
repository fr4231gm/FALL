/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.member;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Brotherhood;
import domain.Enrolment;
import controllers.AbstractController;
import services.BrotherhoodService;
import services.EnrolmentService;

@Controller
@RequestMapping("/enrolment/member")
public class EnrolmentMemberController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public EnrolmentMemberController() {
		super();
	}
	
	// Services
	@Autowired
	private EnrolmentService enrolmentService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;

	// List of all my enrollments	
	@RequestMapping("/list")
	public ModelAndView listMyEnrolments() {
		//Initialize variables
		ModelAndView result;
		Collection<Enrolment> enrolmentsList;
		Locale locale;

		locale = LocaleContextHolder.getLocale();
		
		//Add the list with enrollments that belongs to the user logged
		enrolmentsList=this.enrolmentService.findAllEnrolmentsByPrincipal();

		//Create the Model And View
		result = new ModelAndView("enrolment/member/list");
		
		//Inject the elements in the view
		result.addObject("enrolments", enrolmentsList);
		result.addObject("requestURI", "/enrolment/member/list.do");
		result.addObject("language", locale);
		return result;
	}
	
	// Enroll Into A brotherhood
	@RequestMapping(value = "/enroll",
			method = RequestMethod.GET, 
			params = {"brotherhoodId"})
	public ModelAndView enroll(int brotherhoodId) {
		//Initialize variables
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		
		//Create the ModelAndView
		result = new ModelAndView("brotherhood/list");

		//Try To Enroll into the brotherhood and Inject the result message
		try {
			this.enrolmentService.enroll(brotherhoodId);
			result.addObject("Enrolresult", "success");
			
		}catch (Throwable oops){
			result.addObject("Enrolresult", "fail");
		}
		
		//List all the brotherhoods where the member is not Enrolled
		brotherhoods = this.brotherhoodService.findBrotherhoodsWhithoutEnrolmentByPrincipal();
		
		//Inject the elements in the view
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("enroll", true);
		result.addObject("requestURI", "/brotherhood/member/list/notenrolled.do");
		
		return result;
	}

	// Leave a brotherhood
	@RequestMapping(value = "/dropOut",
			method = RequestMethod.GET, 
			params = {"enrolmentId"})
	public ModelAndView leave(int enrolmentId) {
		//Initialize variables
		ModelAndView result;
		
		//Create the ModelAndView
		result = new ModelAndView("redirect:/enrolment/member/list.do");

		//Try To Leave  the brotherhood
		try {
			this.enrolmentService.leave(enrolmentId);
		}catch (Throwable oops){
			result.addObject("message", "enrolment.commit.error");
		}
		
		return result;
	}

}
