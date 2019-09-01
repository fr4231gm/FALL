
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PositionService;
import services.SponsorshipService;
import domain.Actor;
import domain.Auditor;
import domain.Position;
import domain.Sponsorship;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructors -----------------------------------------------------------

	public PositionController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.findPositionsNotCancelledFinalMode();

		result = new ModelAndView("position/list");
		result.addObject("general", true);
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"companyId"
	})
	public ModelAndView list(@RequestParam final int companyId) {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.findPositionsFinalModeByCompany(companyId);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do?companyId=" + companyId);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"positionId"
	})
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;

		// Initialize variables
		Position position;
		Sponsorship sponsorship;
		position = this.positionService.findOne(positionId);
		sponsorship = this.sponsorshipService.selectRandomSponsorshipIfAny(position.getId());
		if (sponsorship == null) {
			result = new ModelAndView("position/display");
			result.addObject("position", position);
		} else {
			result = new ModelAndView("position/display");
			result.addObject("position", position);

			result.addObject("bannerSponsorship", sponsorship.getBanner());
			result.addObject("targetURLbanner", sponsorship.getTargetPage());
		}
		try {
			final Actor principal = this.actorService.findByPrincipal();
			if (principal instanceof Auditor) {
				final Auditor a = (Auditor) principal;
				if (!a.getPositions().contains(position))
					result.addObject("asignable", true);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/position/displayAnonymous.do?positionId=" + positionId);

		}
		return result;
	}

	@RequestMapping(value = "/displayAnonymous", method = RequestMethod.GET, params = {
		"positionId"
	})
	public ModelAndView displayAnonymous(@RequestParam final int positionId) {
		ModelAndView res;

		// Initialize variables
		Position position;
		Sponsorship sponsorship;
		position = this.positionService.findOne(positionId);
		sponsorship = this.sponsorshipService.selectRandomSponsorshipIfAny(position.getId());
		
		try {
			if (sponsorship == null) {
				res = new ModelAndView("position/display");
				res.addObject("position", position);
			} else {
				res = new ModelAndView("position/display");
				res.addObject("position", position);

				res.addObject("bannerSponsorship", sponsorship.getBanner());
				res.addObject("targetURLbanner", sponsorship.getTargetPage());
			}

		} catch (final Throwable oops) {
			res = new ModelAndView("position/display");
			res.addObject("position", position);
		}
		return res;
	}

	@RequestMapping(value = "/listSearch", method = RequestMethod.GET, params = {
		"keyword"
	})
	public ModelAndView listSearch(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.searchPositionAnonymous(keyword);

		result = new ModelAndView("position/list");

		result = new ModelAndView("position/list");
		result.addObject("general", true);
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/listSearch.do");

		return result;
	}

}
