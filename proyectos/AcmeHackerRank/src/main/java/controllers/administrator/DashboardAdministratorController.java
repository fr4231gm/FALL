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
import domain.Hacker;
import domain.Position;

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
	
		// C-level attributes -------------------------------------------------
		
		Collection<Double> findAvgMinMaxStddevPositionsPerCompany;
		Collection<Double> findAvgMinMaxStddevApplicationsPerHacker;
		List<Company> findTop5PublishableCompanies;
		List<Hacker> findTop5ApplyHacker;
		Collection<Double> findAvgMinMaxStddevSalaryOffered;
		Collection<Position> findBestPositionBySalary;
		Collection<Position> findWorstPositionBySalary;
		
		// B- level attributes ------------------------------------------------
		Collection<Double> findMinMaxAvgStddevCurriculaPerHacker;
		Collection<Double> findMinMaxAvgStdevFindersResults;
		Double findRatioEmptyVsNotEmptyFinders;
				
	
		//Setting language-----------------------------------------------------
		
		// To get the language on each momment
		Locale locale;
		locale = LocaleContextHolder.getLocale();
		
		
		// Create res view pointing to view------------------------------------
		res = new ModelAndView("administrator/dashboard");

		
		// C-level assignament ------------------------------------------------
		findAvgMinMaxStddevPositionsPerCompany = this.administratorService.findAvgMinMaxStddevPositionsPerCompany();
		findAvgMinMaxStddevApplicationsPerHacker = this.administratorService.findAvgMinMaxStddevApplicationsPerHacker();
		findTop5PublishableCompanies = this.administratorService.findTop5PublishableCompanies();
		findTop5ApplyHacker = this.administratorService.findTop5ApplyHackers();
		findAvgMinMaxStddevSalaryOffered = this.administratorService.findAvgMinMaxStddevSalaryOffered();
		findBestPositionBySalary = this.administratorService.findBestPositionBySalary();
		findWorstPositionBySalary = this.administratorService.findWorstPositionBySalary();
		
		// B-level assignament ------------------------------------------------
		findMinMaxAvgStddevCurriculaPerHacker = this.administratorService.findMinMaxAvgStddevCurriculaPerHacker();
		findMinMaxAvgStdevFindersResults = this.administratorService.findMinMaxAvgStdevFindersResults();
		findRatioEmptyVsNotEmptyFinders = this.administratorService.findRatioEmptyVsNotEmptyFinders();
		
		// Adding C-level attributes to ModelAndView---------------------------
		res.addObject("findAvgMinMaxStddevPositionsPerCompany", findAvgMinMaxStddevPositionsPerCompany);
		res.addObject("findAvgMinMaxStddevApplicationsPerHacker", findAvgMinMaxStddevApplicationsPerHacker);
		res.addObject("findTop5PublishableCompanies", findTop5PublishableCompanies);
		res.addObject("findTop5ApplyHacker", findTop5ApplyHacker);
		res.addObject("findAvgMinMaxStddevSalaryOffered", findAvgMinMaxStddevSalaryOffered);
		res.addObject("findBestPositionBySalary", findBestPositionBySalary);
		res.addObject("findWorstPositionBySalary", findWorstPositionBySalary);
		
		// Adding C-level attributes to ModelAndView---------------------------
		res.addObject("findMinMaxAvgStddevCurriculaPerHacker", findMinMaxAvgStddevCurriculaPerHacker);
		res.addObject("findMinMaxAvgStdevFindersResults", findMinMaxAvgStdevFindersResults);
		res.addObject("findRatioEmptyVsNotEmptyFinders", findRatioEmptyVsNotEmptyFinders);
		
		
		
		res.addObject("requestURI", "administrator/list.do");
		res.addObject("language", locale);
		
		return res;
	
	}
			
}
