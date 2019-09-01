
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/position/auditor")
public class PositionAuditorController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/assign", method = RequestMethod.GET, params = {
		"positionId"
	})
	public ModelAndView assign(@RequestParam final int positionId) {
		ModelAndView result;

		// Initialize variables
		Position position;
		final Auditor principal = this.auditorService.findByPrincipal();
		position = this.positionService.findOne(positionId);
		if (position.getIsCancelled() != true)
			try {
				final Collection<Position> positions = principal.getPositions();
				positions.add(position);
				principal.setPositions(positions);
				this.auditorService.save(principal);
			} catch (final Throwable oops) {
			}
		result = new ModelAndView("redirect:/audit/auditor/list.do");
		return result;
	}

}
