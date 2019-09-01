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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ProcessionService;
import domain.Actor;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	// Singletons
	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public ProcessionController() {
		super();
	}

	// List the procession of a brotherhood
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"brotherhoodId"
	})
	public ModelAndView list(final int brotherhoodId) {
		ModelAndView res;
		Collection<Procession> processions;

		boolean permiso = false;
		try {

			final Actor principal = this.actorService.findByPrincipal();
			if (principal instanceof Brotherhood)
				if (principal.getId() == brotherhoodId)
					permiso = true;

			processions = this.processionService.findProcessionsNoDraftByBrotherhood(brotherhoodId);

			res = new ModelAndView("procession/list");
			res.addObject("processions", processions);
			res.addObject("requestURI", "procession/list.do?brotherhoodId=" + brotherhoodId);
			res.addObject("permiso", permiso);
		} catch (final Throwable oops) {
			processions = this.processionService.findProcessionsNoDraftByBrotherhood(brotherhoodId);

			res = new ModelAndView("procession/list");
			res.addObject("processions", processions);
			res.addObject("requestURI", "procession/list.do?brotherhoodId=" + brotherhoodId);
			res.addObject("permiso", permiso);
		}
		return res;
	}

	// DISPLAY

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"processionId"
	})
	public ModelAndView display(final int processionId) {
		ModelAndView res;

		Procession procession;

		try {
			procession = this.processionService.findOne(processionId);

			res = new ModelAndView("procession/display");
			res.addObject("procession", procession);
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notfind");
		}

		return res;
	}
}
