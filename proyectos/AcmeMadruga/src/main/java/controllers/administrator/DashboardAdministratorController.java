package controllers.administrator;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import domain.Brotherhood;
import domain.Member;
import domain.Position;
import domain.Procession;

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
		
		//C-level attributes --------------------------------------------------
		Collection<Double> memberPerBrotherhoodStats;
		List<Brotherhood> largestBrotherhoods;
		List<Brotherhood> smallestBrotherhoods;
		Double ratioPendingRequests;
		Double ratioApprovedRequests;
		Double ratioRejectedRequests;
		Collection<List<Double>> processionAndRatiosPendingApprovedRejected;
		Collection<Procession> processionsOrganizedLess30Days;
		Collection<Member> members10PercentMaximumRequestAccepted;
		Collection<Position> positionHistogram;
		
		//B-level attributes---------------------------------------------------
		Collection<Double>	ratioMinMaxAvgDesvBrotherhoodsPerArea;
		Collection<Integer> countBrotherhoodsPerArea;
		Collection<Double>	minMaxAvgDesvResultsPerFinder;
		Double	ratioEmptyvsNonEmptyFinders;
		
		//A+-level attributes---------------------------------------------------
		Double spammersRatio;
		Double polarityAerage;
		
		//Setting language-----------------------------------------------------
		// To get the language on each momment
		Locale locale;

		locale = LocaleContextHolder.getLocale();
		
		//Setting date to find following processions---------------------------
		
		//calcular current date y current date + 30 dias
		Date currentDate;
		currentDate = Calendar.getInstance().getTime();
		Calendar c = Calendar.getInstance();
		//Calendar d = Calendar.getInstance();
		c.setTime(currentDate);
		//d.setTime(currentDate);
		c.add(Calendar.DAY_OF_YEAR, 30);
		//d.add(Calendar.DAY_OF_YEAR, -60);
		Date currentDatePlusOneMonth = c.getTime();
		//currentDate = d.getTime();
		
		// Create res view pointing to view------------------------------------
		res = new ModelAndView("administrator/dashboard");
		
		//C-level assignament--------------------------------------------------
		memberPerBrotherhoodStats = this.administratorService.findMemberPerBrotherhoodStats();
		largestBrotherhoods = this.administratorService.findLargestBrotherhoods();
		smallestBrotherhoods = this.administratorService.findSmallestBrotherhoods();
		ratioPendingRequests = this.administratorService.findRatioRequestsByStatusPending();
		ratioApprovedRequests = this.administratorService.findRatioRequestsByStatusApproved();
		ratioRejectedRequests = this.administratorService.findRatioRequestsByStatusRejected();
		processionAndRatiosPendingApprovedRejected = this.administratorService.getProcessionAndRatiosPendingApprovedRejected();
		processionsOrganizedLess30Days = this.administratorService.findFollowingProcessions(currentDate, currentDatePlusOneMonth);
		members10PercentMaximumRequestAccepted = this.administratorService.findMembersApproved();
		positionHistogram = this.administratorService.findPositionsHistogram();
		
		//B-level assignament--------------------------------------------------
		ratioMinMaxAvgDesvBrotherhoodsPerArea = this.administratorService.findRatioMinMaxAvgDesvBrotherhoodsPerArea();
		countBrotherhoodsPerArea = this.administratorService.findCountBrotherhoodsPerArea();
		minMaxAvgDesvResultsPerFinder = this.administratorService.findMinMaxAvgStdevFindersResults();
		ratioEmptyvsNonEmptyFinders = this.administratorService.findRatioEmptyVsNotEmptyFinders();
		
		//A+ -level assignament--------------------------------------------------
		spammersRatio = this.administratorService.findSpammersRatio();
		polarityAerage = this.administratorService.findPolarityAverage();
		
		
		//Adding C-level attributes to ModelAndView----------------------------
		res.addObject("memberPerBrotherhoodStats", memberPerBrotherhoodStats);
		res.addObject("largestBrotherhoods", largestBrotherhoods);
		res.addObject("smallestBrotherhoods", smallestBrotherhoods);
		res.addObject("ratioPendingRequests", ratioPendingRequests);
		res.addObject("ratioApprovedRequests", ratioApprovedRequests);
		res.addObject("ratioRejectedRequests", ratioRejectedRequests);
		res.addObject("processionAndRatiosPendingApprovedRejected", processionAndRatiosPendingApprovedRejected);
		res.addObject("processionsOrganizedLess30Days", processionsOrganizedLess30Days);
		res.addObject("members10PercentMaximumRequestAccepted", members10PercentMaximumRequestAccepted);
		res.addObject("positionHistogram", positionHistogram);
		res.addObject("language", locale);
		
		//Adding B-level attributes to ModelAndView----------------------------
		res.addObject("ratioMinMaxAvgDesvBrotherhoodsPerArea", ratioMinMaxAvgDesvBrotherhoodsPerArea);
		res.addObject("countBrotherhoodsPerArea", countBrotherhoodsPerArea);
		res.addObject("minMaxAvgDesvResultsPerFinder", minMaxAvgDesvResultsPerFinder);
		res.addObject("ratioEmptyvsNonEmptyFinders", ratioEmptyvsNonEmptyFinders);
		
		//Adding A+-level attributes to ModelAndView----------------------------
		res.addObject("spammersRatio",spammersRatio);
		res.addObject("polarityAverage",polarityAerage);
		
		res.addObject("requestURI", "administrator/list.do");
		
		return res;
	}
}
