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
import domain.Customer;
import domain.Designer;
import domain.Provider;

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
		
		Collection<Double> findAvgMinMaxStddevOrdersPerCustomer;
		Collection<Double> findAvgMinMaxStddevApplicationsPerOrder;
		Collection<Double> findAvgMinMaxStddevOfferedPriceOfApplications;
		Double findRatioApplicationsByStatusPending;
		Double findRatioApplicationsByStatusApproved;
		Double findRatioApplicationsByStatusRejected;
		List<Customer> findActiveCustomers;
		List<Company> findActiveCompanies;
		
		// B-level attributes -------------------------------------------------
		Collection<Double> findAvgMinMaxStddevSponsorshipsPerPost;
		Collection<Double> findMinMaxAvgStddevPrintersPerCompany;		
		Double findRatioOnPrinterSpoolerVsPublished;
		Double findRatioPostWithAndWithoutSponsorships;
		List<Designer> findTop5DesignersByPostsPublished;
		List<Designer> findTop5DesignersByScore;
		List<Designer> findTop5DesignersBySponsorshipsReceived;
		List<Company> findCompaniesWithMorePrinters;
		List<Provider> findTop5Providers;
		
		// A-level attributes -------------------------------------------------
		List<Customer> findTop5Customers;
		List<Company> findCompaniesWithMoreSpools;
		List<Company> findTop5ActiveCompanies;
		
		
		//Setting language-----------------------------------------------------
		
		// To get the language on each momment
		Locale locale;
		locale = LocaleContextHolder.getLocale();
		
		
		// Create res view pointing to view------------------------------------
		res = new ModelAndView("administrator/dashboard");

		/**************************ACME-HACKER-RANK***************************/
		
		// C-level assignament ------------------------------------------------
		findAvgMinMaxStddevOrdersPerCustomer = this.administratorService.findAvgMinMaxStddevOrdersPerCustomer();
		findAvgMinMaxStddevApplicationsPerOrder = this.administratorService.findAvgMinMaxStddevApplicationsPerOrder();
		findAvgMinMaxStddevOfferedPriceOfApplications = this.administratorService.findAvgMinMaxStddevOfferedPriceOfApplications();
		findRatioApplicationsByStatusPending = this.administratorService.findRatioApplicationsByStatusPending();
		findRatioApplicationsByStatusApproved = this.administratorService.findRatioApplicationsByStatusAccepted();
		findRatioApplicationsByStatusRejected = this.administratorService.findRatioApplicationsByStatusRejected();
		findActiveCustomers = this.administratorService.findActiveCustomers();
		findActiveCompanies = this.administratorService.findActiveCompanies();
		
		// B-level assignament ------------------------------------------------
		findAvgMinMaxStddevSponsorshipsPerPost = this.administratorService.findAvgMinMaxStddevSponsorshipsPerPost();
		findMinMaxAvgStddevPrintersPerCompany = this.administratorService.findMinMaxAvgStddevPrintersPerCompany();		
		findRatioOnPrinterSpoolerVsPublished = this.administratorService.findRatioOnPrinterSpoolerVsPublished();
		findRatioPostWithAndWithoutSponsorships = this.administratorService.findRatioPostWithAndWithoutSponsorships();
		findTop5DesignersByPostsPublished = this.administratorService.findTop5DesignersByPostsPublished();
		findTop5DesignersByScore = this.administratorService.findTop5DesignersByScore();
		findTop5DesignersBySponsorshipsReceived = this.administratorService.findTop5DesignersBySponsorshipsReceived();
		findCompaniesWithMorePrinters = this.administratorService.findCompaniesWithMorePrinters();
		findTop5Providers = this.administratorService.findTop5Providers();
		
		// A-level assignament ------------------------------------------------
		findTop5Customers = this.administratorService.findTop5Customers();
		findCompaniesWithMoreSpools = this.administratorService.findCompaniesWithMoreSpools();
		findTop5ActiveCompanies = this.administratorService.findTop5ActiveCompanies();

		
		
		// Adding C-level attributes to ModelAndView---------------------------
		res.addObject("findAvgMinMaxStddevOrdersPerCustomer", findAvgMinMaxStddevOrdersPerCustomer);
		res.addObject("findAvgMinMaxStddevApplicationsPerOrder", findAvgMinMaxStddevApplicationsPerOrder);
		res.addObject("findAvgMinMaxStddevOfferedPriceOfApplications", findAvgMinMaxStddevOfferedPriceOfApplications);
		res.addObject("findRatioApplicationsByStatusPending", findRatioApplicationsByStatusPending);
		res.addObject("findRatioApplicationsByStatusApproved", findRatioApplicationsByStatusApproved);
		res.addObject("findRatioApplicationsByStatusRejected", findRatioApplicationsByStatusRejected);
		res.addObject("findActiveCustomers", findActiveCustomers);
		res.addObject("findActiveCompanies", findActiveCompanies);
		
		// Adding B-level attributes to ModelAndView---------------------------
		res.addObject("findAvgMinMaxStddevSponsorshipsPerPost", findAvgMinMaxStddevSponsorshipsPerPost);
		res.addObject("findMinMaxAvgStddevPrintersPerCompany", findMinMaxAvgStddevPrintersPerCompany);
		res.addObject("findRatioOnPrinterSpoolerVsPublished", findRatioOnPrinterSpoolerVsPublished);
		res.addObject("findRatioPostWithAndWithoutSponsorships", findRatioPostWithAndWithoutSponsorships);
		res.addObject("findTop5DesignersByPostsPublished", findTop5DesignersByPostsPublished);
		res.addObject("findTop5DesignersByScore", findTop5DesignersByScore);
		res.addObject("findTop5DesignersBySponsorshipsReceived", findTop5DesignersBySponsorshipsReceived);
		res.addObject("findCompaniesWithMorePrinters", findCompaniesWithMorePrinters);
		res.addObject("findTop5Providers", findTop5Providers);
		
		// Adding A-level attributes to ModelAndView---------------------------
		res.addObject("findTop5Customers", findTop5Customers);
		res.addObject("findCompaniesWithMoreSpoolers", findCompaniesWithMoreSpools);
		res.addObject("findTop5ActiveCompanies", findTop5ActiveCompanies);

		// Adding globals to ModelAndView--------------------------------------
		res.addObject("requestURI", "administrator/list.do");
		res.addObject("language", locale);
		
		return res;
	
	}
			
}
