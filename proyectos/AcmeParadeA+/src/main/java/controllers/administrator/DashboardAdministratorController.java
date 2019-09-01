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
import domain.Chapter;
import domain.Member;
import domain.Parade;
import domain.Position;
import domain.Sponsor;

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
		
		/*****************************ACME-MADRUGA****************************/
		
		//C-level attributes --------------------------------------------------
		/*****************************ACME-MADRUGA*****************************/
		Collection<Double> memberPerBrotherhoodStats;
		List<Brotherhood> largestBrotherhoods;
		List<Brotherhood> smallestBrotherhoods;
		Double ratioPendingRequests;
		Double ratioApprovedRequests;
		Double ratioRejectedRequests;
		Collection<List<Double>> paradeAndRatiosPendingApprovedRejected;
		Collection<Parade> paradesOrganizedLess30Days;
		Collection<Member> members10PercentMaximumRequestAccepted;
		Collection<Position> positionHistogram;
		
		/*****************************ACME-PARADE*****************************/
		Collection<Double> recordsPerHistoryStats;
		Brotherhood largestBrotherhood;
		List<Brotherhood> brotherhoodsLargestHistory;
		
		//B-level attributes---------------------------------------------------
		
		/*****************************ACME-MADRUGA*****************************/
		Collection<Double>	ratioMinMaxAvgDesvBrotherhoodsPerArea;
		Collection<Integer> countBrotherhoodsPerArea;
		Collection<Double>	minMaxAvgDesvResultsPerFinder;
		Double	ratioEmptyvsNonEmptyFinders;
		/*****************************ACME-PARADE*****************************/
		Double ratioAreasNotCoordinated;
		Collection<Double> paradesPerChaptersStats;
		Collection<Chapter> activeChapters;
		Double ratioDraftVsFinalModeParades;
		Collection<Double> ratioParadesByStatus;
		
		//A-level attributes--------------------------------------------------
		/*****************************ACME-PARADE*****************************/
		Double ratioActiveSponsorships;
		Collection<Double> sponsorshipPerSponsorStats;
		List<Sponsor> top5Sponsors;
		
		//A+-level attributes--------------------------------------------------
		/*****************************ACME-MADRUGA*****************************/
		Double spammersRatio;
		Double polarityAerage;
		
		
		//Setting language-----------------------------------------------------
		
		// To get the language on each momment
		Locale locale;

		locale = LocaleContextHolder.getLocale();
		
		//Setting date to find following parades---------------------------
		
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
		/*****************************ACME-MADRUGA****************************/
		memberPerBrotherhoodStats = this.administratorService.findMemberPerBrotherhoodStats();
		largestBrotherhoods = this.administratorService.findLargestBrotherhoods();
		smallestBrotherhoods = this.administratorService.findSmallestBrotherhoods();
		ratioPendingRequests = this.administratorService.findRatioRequestsByStatusPending();
		ratioApprovedRequests = this.administratorService.findRatioRequestsByStatusApproved();
		ratioRejectedRequests = this.administratorService.findRatioRequestsByStatusRejected();
		paradeAndRatiosPendingApprovedRejected = this.administratorService.getParadeAndRatiosPendingApprovedRejected();
		paradesOrganizedLess30Days = this.administratorService.findFollowingParades(currentDate, currentDatePlusOneMonth);
		members10PercentMaximumRequestAccepted = this.administratorService.findMembersApproved();
		positionHistogram = this.administratorService.findPositionsHistogram();
		
		/*****************************ACME-PARADE*****************************/
		recordsPerHistoryStats = this.administratorService.findAvgMinMaxStdevRecordsPerHistory();
		largestBrotherhood = this.administratorService.findLargestBrotherhood();
		brotherhoodsLargestHistory = this.administratorService.findBrotherhoodsLargestHistory();
		
		//B-level assignament--------------------------------------------------
		
		/*****************************ACME-MADRUGA****************************/
		ratioMinMaxAvgDesvBrotherhoodsPerArea = this.administratorService.findRatioMinMaxAvgDesvBrotherhoodsPerArea();
		countBrotherhoodsPerArea = this.administratorService.findCountBrotherhoodsPerArea();
		minMaxAvgDesvResultsPerFinder = this.administratorService.findMinMaxAvgStdevFindersResults();
		ratioEmptyvsNonEmptyFinders = this.administratorService.findRatioEmptyVsNotEmptyFinders();
		
		/*****************************ACME-PARADE*****************************/
		ratioAreasNotCoordinated = this.administratorService.findRatioAreasNotCoordinated();
		paradesPerChaptersStats = this.administratorService.findAvgMinMaxStdevParadesPerChapters();
		activeChapters = this.administratorService.findActiveChapters();
		ratioDraftVsFinalModeParades = this.administratorService.findRatioDraftVsFinalModeParades();
		ratioParadesByStatus = this.administratorService.findRatioParadesByStatus();
		
		//A-level assignament-------------------------------------------------
		/*****************************ACME-PARADE*****************************/
		ratioActiveSponsorships = this.administratorService.findRatioActiveSponsorships();
		sponsorshipPerSponsorStats = this.administratorService.findAvgMinMaxStdevSponsorshipsPerSponsor();
		top5Sponsors = this.administratorService.findTop5Sponsors();
		
		//A+-level assignament--------------------------------------------------
		spammersRatio = this.administratorService.findSpammersRatio();
		polarityAerage = this.administratorService.findPolarityAverage();
		
		
		//Adding C-level attributes to ModelAndView----------------------------
		/*****************************ACME-MADRUGA*****************************/
		res.addObject("memberPerBrotherhoodStats", memberPerBrotherhoodStats);
		res.addObject("largestBrotherhoods", largestBrotherhoods);
		res.addObject("smallestBrotherhoods", smallestBrotherhoods);
		res.addObject("ratioPendingRequests", ratioPendingRequests);
		res.addObject("ratioApprovedRequests", ratioApprovedRequests);
		res.addObject("ratioRejectedRequests", ratioRejectedRequests);
		res.addObject("paradeAndRatiosPendingApprovedRejected", paradeAndRatiosPendingApprovedRejected);
		res.addObject("paradesOrganizedLess30Days", paradesOrganizedLess30Days);
		res.addObject("members10PercentMaximumRequestAccepted", members10PercentMaximumRequestAccepted);
		res.addObject("positionHistogram", positionHistogram);
		res.addObject("language", locale);
		/*****************************ACME-PARADE*****************************/
		res.addObject("recordsPerHistoryStats", recordsPerHistoryStats);
		res.addObject("largestBrotherhood", largestBrotherhood);
		res.addObject("brotherhoodsLargestHistory", brotherhoodsLargestHistory);
		
		//Adding B-level attributes to ModelAndView----------------------------
		/*****************************ACME-MADRUGA*****************************/
		res.addObject("ratioMinMaxAvgDesvBrotherhoodsPerArea", ratioMinMaxAvgDesvBrotherhoodsPerArea);
		res.addObject("countBrotherhoodsPerArea", countBrotherhoodsPerArea);
		res.addObject("minMaxAvgDesvResultsPerFinder", minMaxAvgDesvResultsPerFinder);
		res.addObject("ratioEmptyvsNonEmptyFinders", ratioEmptyvsNonEmptyFinders);
		/*****************************ACME-PARADE*****************************/
		res.addObject("ratioAreasNotCoordinated", ratioAreasNotCoordinated);
		res.addObject("paradesPerChaptersStats", paradesPerChaptersStats);
		res.addObject("activeChapters", activeChapters);
		res.addObject("ratioDraftVsFinalModeParades", ratioDraftVsFinalModeParades);
		res.addObject("ratioParadesByStatus", ratioParadesByStatus);
		
		//Adding A-level attributes to ModelAndView----------------------------
		/*****************************ACME-PARADE*****************************/
		res.addObject("ratioActiveSponsorships", ratioActiveSponsorships);
		res.addObject("sponsorshipPerSponsorStats", sponsorshipPerSponsorStats);
		res.addObject("top5Sponsors", top5Sponsors);
		
		//Adding A+-level attributes to ModelAndView----------------------------
		res.addObject("spammersRatio",spammersRatio);
		res.addObject("polarityAverage",polarityAerage);
		
		res.addObject("requestURI", "administrator/list.do");
		
		return res;
	}
}
