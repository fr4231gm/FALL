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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/administrator")
public class SponsorshipAdministratorController extends AbstractController {

	// Singletons

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	// Constructors -----------------------------------------------------------

	public SponsorshipAdministratorController() {
		super();
	}

	// List --------------------------------------------------------------------
	@RequestMapping(value = "/listSponsorships", method = RequestMethod.GET)
	public ModelAndView listSponsorships() {
		ModelAndView res;
		Collection<Sponsorship> sponsorships = new ArrayList<>();
		final Calendar fecha = Calendar.getInstance();
		final int mes = fecha.get(Calendar.MONTH) + 1;
		final int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		try {
			sponsorships = this.sponsorshipService.findSponsorshipsWithExpiredCreditCards(anyo, mes);
		} catch (final Throwable oops) {

		}
		res = new ModelAndView("administrator/listSponsorships");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsorship/administrator/listSponsorships.do");
		res.addObject("desaparece", false);
		return res;
	}

	// Deactivate--------------------------------------------------------------
	@RequestMapping(value = "/deactiveSponsorships", method = RequestMethod.GET)
	public ModelAndView deactiveSponsorships() {
		Collection<Sponsorship> sp = new ArrayList<>();
		ModelAndView res;
		Administrator principal;
		final Calendar fecha = Calendar.getInstance();
		final int mes = fecha.get(Calendar.MONTH) + 1;
		final int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		principal = this.administratorService.findByPrincipal();
		sp = this.sponsorshipService.findSponsorshipsWithExpiredCreditCards(anyo, mes);
		try {
			this.sponsorshipService.deactivateAllExpired(principal.getId(), sp);
			res = new ModelAndView("administrator/listSponsorships");
			res.addObject("exito", "sponsorship.administrator.deactivate.success");
			res.addObject("desaparece", true);
		} catch (final Throwable oops) {

			//llamar al método para volver a coger los sponsorships no desactivados
			Collection<Sponsorship> sponsorships2 = new ArrayList<>();
			sponsorships2 = this.sponsorshipService.findSponsorshipsWithExpiredCreditCards(anyo, mes);

			res = new ModelAndView("administrator/listSponsorships");
			res.addObject("message", "sponsorship.commit.error.deactivate");
			res.addObject("sponsorships", sponsorships2);
			res.addObject("requestURI", "sponsorship/administrator/deactiveSponsorships.do");
			res.addObject("desaparece", false);

		}
		return res;
	}

}
