/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Service
	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ConfigurationService	configService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		final List<Object[]> fupPerUser = new ArrayList<Object[]>(this.administratorService.findFixUpTaskPerUserStats());
		final Double avgFup = (Double) fupPerUser.get(0)[0];
		final Integer minFup = (Integer) fupPerUser.get(0)[1];
		final Integer maxFup = (Integer) fupPerUser.get(0)[2];
		final Double stddevFup = (Double) fupPerUser.get(0)[3];

		final List<Object[]> appPerFup = new ArrayList<Object[]>(this.administratorService.findApplicationPerFixUpTaskStats());
		final Double avgApp = (Double) appPerFup.get(0)[0];
		final Integer minApp = (Integer) appPerFup.get(0)[1];
		final Integer maxApp = (Integer) appPerFup.get(0)[2];
		final Double stddevApp = (Double) appPerFup.get(0)[3];

		final List<Object[]> fupMaxPrice = new ArrayList<Object[]>(this.administratorService.findMaxPricePerFixUpTaskStats());
		final Double avgFupMaxPrice = (Double) fupMaxPrice.get(0)[0];
		final Double minFupMaxPrice = (Double) fupMaxPrice.get(0)[1];
		final Double maxFupMaxPrice = (Double) fupMaxPrice.get(0)[2];
		final Double stddevFupMaxPrice = (Double) fupMaxPrice.get(0)[3];

		final List<Object[]> appPrice = new ArrayList<Object[]>(this.administratorService.findPricePerApplicationStats());
		final Double avgAppPrice = (Double) appPrice.get(0)[0];
		final Double minAppPrice = (Double) appPrice.get(0)[1];
		final Double maxAppPrice = (Double) appPrice.get(0)[2];
		final Double stddevAppPrice = (Double) appPrice.get(0)[3];

		final Double pendingRatio = this.administratorService.findRatioOfPendingApplications();

		final Double acceptedRatio = this.administratorService.findPendingApplications();

		final Double rejectedRatio = this.administratorService.findRatioOfRejectedApplications();

		final Double expiredRatio = this.administratorService.findRatioOfPendingApplications();

		final List<Customer> custFupAboveAvg = new ArrayList<Customer>(this.administratorService.findCustomerFixUpTasksAboveAverage());

		final List<HandyWorker> hwAppAboveAvg = new ArrayList<HandyWorker>(this.administratorService.findHandyWorkersApplicationAboveAverage());

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgFup", avgFup);
		result.addObject("minFup", minFup);
		result.addObject("maxFup", maxFup);
		result.addObject("stddevFup", stddevFup);

		result.addObject("avgApp", avgApp);
		result.addObject("minApp", minApp);
		result.addObject("maxApp", maxApp);
		result.addObject("stddevApp", stddevApp);

		result.addObject("avgFupMaxPrice", avgFupMaxPrice);
		result.addObject("minFupMaxPrice", minFupMaxPrice);
		result.addObject("maxFupMaxPrice", maxFupMaxPrice);
		result.addObject("stddevFupMaxPrice", stddevFupMaxPrice);

		result.addObject("avgAppPrice", avgAppPrice);
		result.addObject("minAppPrice", minAppPrice);
		result.addObject("maxAppPrice", maxAppPrice);
		result.addObject("stddevAppPrice", stddevAppPrice);

		result.addObject("pendingRatio", pendingRatio);

		result.addObject("acceptedRatio", acceptedRatio);

		result.addObject("rejectedRatio", rejectedRatio);

		result.addObject("expiredRatio", expiredRatio);

		result.addObject("custFupAboveAvg", custFupAboveAvg);

		result.addObject("hwAppAboveAvg", hwAppAboveAvg);
		
		result.addObject("configuration", configService.findConfiguration());
		return result;
	}
}
