
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	positionService;


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

		position = this.positionService.findOne(positionId);

		result = new ModelAndView("position/display");
		result.addObject("position", position);
		return result;
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
