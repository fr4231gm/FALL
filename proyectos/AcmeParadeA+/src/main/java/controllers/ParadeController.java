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
import services.ParadeService;
import services.SponsorshipService;
import domain.Actor;
import domain.Brotherhood;
import domain.Parade;
import domain.Sponsorship;

@Controller
@RequestMapping("/parade")
public class ParadeController extends AbstractController {

	// Singletons
	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private SponsorshipService sponsorshipService;


	// Constructors -----------------------------------------------------------

	public ParadeController() {
		super();
	}

	// List the parade of a brotherhood
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"brotherhoodId"
	})
	public ModelAndView list(final int brotherhoodId) {
		ModelAndView res;
		Collection<Parade> parades;

		boolean permiso = false;
		try {

			final Actor principal = this.actorService.findByPrincipal();
			if (principal instanceof Brotherhood)
				if (principal.getId() == brotherhoodId)
					permiso = true;

			parades = this.paradeService.findParadesNoDraftByBrotherhood(brotherhoodId);

			res = new ModelAndView("parade/list");

			res.addObject("parades", parades);
			res.addObject("requestURI", "parade/list.do?brotherhoodId=" + brotherhoodId);
			res.addObject("permiso", permiso);
		} catch (final Throwable oops) {
			parades = this.paradeService.findParadesNoDraftByBrotherhood(brotherhoodId);

			res = new ModelAndView("parade/list");

			parades = this.paradeService.findParadesWithStatusAccepted(brotherhoodId);

			res.addObject("parades", parades);
			res.addObject("requestURI", "parade/list.do?brotherhoodId=" + brotherhoodId);
			res.addObject("permiso", permiso);
		}
		return res;
	}

	// DISPLAY

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"paradeId"
	})
	public ModelAndView display(final int paradeId) {
		ModelAndView res;

		Parade parade;
		Sponsorship sponsorship;
		
		try {
			parade = this.paradeService.findOne(paradeId);
			sponsorship = this.sponsorshipService.selectRandomSponsorshipIfAny(parade.getId());
			
			if (sponsorship == null){
				res = new ModelAndView("parade/display");
				res.addObject("parade", parade);
			}else{
				res = new ModelAndView("parade/display");
				res.addObject("parade", parade);
				
				res.addObject("bannerSponsorship", sponsorship.getBanner());
				res.addObject("targetURLbanner", sponsorship.getTargetURL());
			}
			
		} catch (final Throwable oops) {
			res = new ModelAndView("security/pagecharging");
		}

		return res;
	}
}
