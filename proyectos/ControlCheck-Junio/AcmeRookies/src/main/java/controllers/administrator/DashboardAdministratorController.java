/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	//LIST
	
	@RequestMapping("/list")
	public ModelAndView list(){
		
		Assert.notNull(this.administratorService.findByPrincipal());
		
		ModelAndView res;
	
		/**************************ACME-HACKER-RANK***************************/
		
		// C-level attributes -------------------------------------------------
		
		Collection<Double> findAvgMinMaxStddevPositionsPerCompany;
		Collection<Double> findAvgMinMaxStddevApplicationsPerRookie;
		List<Company> findTop5PublishableCompanies;
		List<Rookie> findTop5ApplyRookie;
		Collection<Double> findAvgMinMaxStddevSalaryOffered;
		Collection<Position> findBestPositionBySalary;
		Collection<Position> findWorstPositionBySalary;
		
		// B- level attributes ------------------------------------------------
		Collection<Double> findMinMaxAvgStddevCurriculaPerRookie;
		Collection<Double> findMinMaxAvgStdevFindersResults;
		Double findRatioEmptyVsNotEmptyFinders;
				
		/*****************************ACME-ROOKIE****************************/
		
		// C-level attributes -------------------------------------------------
		Collection<Double> findAvgMinMaxStddevAuditsScore;
		Collection<Double> findAvgMinMaxStddevCompanyScore;
		List<Company> findTop5Companies;
		List<Double> findTopSalaryPositions;
		
		// B-level attributes -------------------------------------------------
		Collection<Double> findMinMaxAvgStddevItemsPerProvider;
		List<Provider> findTop5Providers;
		
		// A-level attributes -------------------------------------------------
		Collection<Double> findAvgMinMaxStddevSponsorshipsPerProvider;
		Collection<Double> findAvgMinMaxStddevSponsorshipsPerPosition;
		Collection<Provider> findActiveProviders;
		
		//Setting language-----------------------------------------------------
		
		// To get the language on each momment
		Locale locale;
		locale = LocaleContextHolder.getLocale();
		
		
		// Create res view pointing to view------------------------------------
		res = new ModelAndView("administrator/dashboard");

		/**************************ACME-HACKER-RANK***************************/
		
		// C-level assignament ------------------------------------------------
		findAvgMinMaxStddevPositionsPerCompany = this.administratorService.findAvgMinMaxStddevPositionsPerCompany();
		findAvgMinMaxStddevApplicationsPerRookie = this.administratorService.findAvgMinMaxStddevApplicationsPerRookie();
		findTop5PublishableCompanies = this.administratorService.findTop5PublishableCompanies();
		findTop5ApplyRookie = this.administratorService.findTop5ApplyRookies();
		findAvgMinMaxStddevSalaryOffered = this.administratorService.findAvgMinMaxStddevSalaryOffered();
		findBestPositionBySalary = this.administratorService.findBestPositionBySalary();
		findWorstPositionBySalary = this.administratorService.findWorstPositionBySalary();
		
		// B-level assignament ------------------------------------------------
		findMinMaxAvgStddevCurriculaPerRookie = this.administratorService.findMinMaxAvgStddevCurriculaPerRookie();
		findMinMaxAvgStdevFindersResults = this.administratorService.findMinMaxAvgStdevFindersResults();
		findRatioEmptyVsNotEmptyFinders = this.administratorService.findRatioEmptyVsNotEmptyFinders();
		
		/*****************************ACME-ROOKIE****************************/
		
		// C-level assignament ------------------------------------------------
		findAvgMinMaxStddevAuditsScore = this.administratorService.findAvgMinMaxStddevAuditsScore();
		findAvgMinMaxStddevCompanyScore = this.administratorService.findAvgMinMaxStddevCompanyScore();
		findTop5Companies = this.administratorService.findTop5Companies();
		findTopSalaryPositions = this.administratorService.findTopSalaryPositions();
		
		// B-level assignament ------------------------------------------------
		findMinMaxAvgStddevItemsPerProvider = this.administratorService.findMinMaxAvgStddevItemsPerProvider();
		findTop5Providers = this.administratorService.findTop5Providers();
		
		// A-level assignament ------------------------------------------------
		findAvgMinMaxStddevSponsorshipsPerProvider = this.administratorService.findAvgMinMaxStddevSponsorshipsPerProvider();
		findAvgMinMaxStddevSponsorshipsPerPosition = this.administratorService.findAvgMinMaxStddevSponsorshipsPerPosition();
		findActiveProviders = this.administratorService.findActiveProviders();
		
		
		/**************************ACME-HACKER-RANK***************************/
		
		// Adding C-level attributes to ModelAndView---------------------------
		res.addObject("findAvgMinMaxStddevPositionsPerCompany", findAvgMinMaxStddevPositionsPerCompany);
		res.addObject("findAvgMinMaxStddevApplicationsPerRookie", findAvgMinMaxStddevApplicationsPerRookie);
		res.addObject("findTop5PublishableCompanies", findTop5PublishableCompanies);
		res.addObject("findTop5ApplyRookie", findTop5ApplyRookie);
		res.addObject("findAvgMinMaxStddevSalaryOffered", findAvgMinMaxStddevSalaryOffered);
		res.addObject("findBestPositionBySalary", findBestPositionBySalary);
		res.addObject("findWorstPositionBySalary", findWorstPositionBySalary);
		
		// Adding B-level attributes to ModelAndView---------------------------
		res.addObject("findMinMaxAvgStddevCurriculaPerRookie", findMinMaxAvgStddevCurriculaPerRookie);
		res.addObject("findMinMaxAvgStdevFindersResults", findMinMaxAvgStdevFindersResults);
		res.addObject("findRatioEmptyVsNotEmptyFinders", findRatioEmptyVsNotEmptyFinders);
		
		/*****************************ACME-ROOKIE****************************/
		
		// Adding C-level attributes to ModelAndView---------------------------
		res.addObject("findAvgMinMaxStddevAuditsScore", findAvgMinMaxStddevAuditsScore);
		res.addObject("findAvgMinMaxStddevCompanyScore", findAvgMinMaxStddevCompanyScore);
		res.addObject("findTop5Companies", findTop5Companies);
		res.addObject("findTopSalaryPositions", findTopSalaryPositions);
		
		// Adding B-level attributes to ModelAndView---------------------------
		res.addObject("findMinMaxAvgStddevItemsPerProvider", findMinMaxAvgStddevItemsPerProvider);
		res.addObject("findTop5Providers", findTop5Providers);
		
		// Adding A-level attributes to ModelAndView---------------------------
		res.addObject("findAvgMinMaxStddevSponsorshipsPerProvider", findAvgMinMaxStddevSponsorshipsPerProvider);
		res.addObject("findAvgMinMaxStddevSponsorshipsPerPosition", findAvgMinMaxStddevSponsorshipsPerPosition);
		res.addObject("findActiveProviders", findActiveProviders);
		
		
		res.addObject("requestURI", "administrator/list.do");
		res.addObject("language", locale);
		
		return res;
	
	}
			
}
