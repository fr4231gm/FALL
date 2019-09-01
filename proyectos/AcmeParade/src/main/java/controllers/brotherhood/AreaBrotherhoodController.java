
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("/area/brotherhood")
public class AreaBrotherhoodController extends AbstractController {

	public AreaBrotherhoodController() {
		super();
	}


	//Recursos externos

	@Autowired
	private AreaService			areaService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Show ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int areaId) {
		ModelAndView result;
		Area area;

		area = this.areaService.findOne(areaId);

		result = new ModelAndView("area/display");

		result.addObject("area", area);
		result.addObject("cancelURI", "area/list.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	ModelAndView list() {
		ModelAndView result;

		if (this.brotherhoodService.findByPrincipal().getArea() != null)
			result = new ModelAndView("redirect:display.do?areaId=" + this.brotherhoodService.findByPrincipal().getArea().getId());
		else {

			Collection<Area> areas;
			areas = this.areaService.findAll();

			result = new ModelAndView("area/list");
			result.addObject("areas", areas);
			result.addObject("requestURI", "area/brotherhood/list.do");

		}
		return result;
	}

}
