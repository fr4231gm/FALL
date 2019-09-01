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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import services.PositionService;
import controllers.AbstractController;
import domain.Enrolment;
import domain.Position;
import forms.EnrolmentForm;

@Controller
@RequestMapping("/position/brotherhood")
public class PositionBrotherhoodController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public PositionBrotherhoodController() {
		super();
	}


	// Service
	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Assign a position to a enrollment
	@RequestMapping(value = "/assign", method = RequestMethod.GET, params = {
		"enrolmentId"
	})
	public ModelAndView assign(final int enrolmentId) {
		//Initialize variables
		ModelAndView result;
		EnrolmentForm enrolmentForm;
		Collection<Position> positions;
		Locale locale;
		Assert.isTrue(this.enrolmentService.findOne(enrolmentId).getBrotherhood().equals(this.brotherhoodService.findByPrincipal()));

		//Gets the language
		locale = LocaleContextHolder.getLocale();

		//Takes the enrollment 
		enrolmentForm = this.enrolmentService.construct(enrolmentId);

		positions = this.positionService.findAll();
		//Create the Model And View
		result = new ModelAndView("position/brotherhood/assign");

		//Inject the elements in the view
		result.addObject("enrolmentForm", enrolmentForm);
		result.addObject("positions", positions);
		result.addObject("language", locale);

		return result;
	}

	//Assign a position to a enrollment
	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = {
		"save"
	})
	public ModelAndView assign(@Valid final EnrolmentForm enrolmentForm, final BindingResult binding) {
		//Initialize variables
		ModelAndView result;
		Enrolment enrolment;
		String messageCode;
		Locale locale;

		if (enrolmentForm.getId() != 0 && this.enrolmentService.findOneToFail(enrolmentForm.getId()).getDropOutMoment() != null)
			result = new ModelAndView("security/notfind");
		else {

			//Gets the language
			locale = LocaleContextHolder.getLocale();

			//try to assign the new position to the enrolment
			try {
				enrolment = this.enrolmentService.reconstruct(enrolmentForm);
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:/enrolment/brotherhood/list/assigned.do");
				messageCode = "enrolment.success";
			} catch (final Throwable opps) {
				result = new ModelAndView();
				messageCode = "enrolment.commit.error";
			}

			//Inject the elements in the view
			result.addObject("message", messageCode);
			result.addObject("language", locale);

		}
		return result;
	}

}
