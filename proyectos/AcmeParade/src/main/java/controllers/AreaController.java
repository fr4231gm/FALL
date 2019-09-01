
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import domain.Area;

@Controller
@RequestMapping("/area")
public class AreaController extends AbstractController {

	public AreaController() {
		super();
	}


	//Recursos

	@Autowired
	private AreaService	areaService;


	// Show ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int areaId) {
		ModelAndView result;
		Area area;

		try {
			area = this.areaService.findOne(areaId);

			result = new ModelAndView("area/display");

			result.addObject("area", area);
			result.addObject("cancelURI", "area/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("security/notfind");
		}

		return result;
	}

	@RequestMapping(value = "/displayByChapter", method = RequestMethod.GET)
	public ModelAndView showByChapter(@RequestParam final int chapterId) {
		ModelAndView result;
		Area area;

		try {
			area = this.areaService.findAreaByChapterId(chapterId);
			if (area == null) {
				result = new ModelAndView("security/notfind");
				result.addObject("message", "notfind.area");
			} else {
				result = new ModelAndView("area/display");

				result.addObject("area", area);
				result.addObject("cancelURI", "area/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("security/notfind");
		}

		return result;
	}
}
