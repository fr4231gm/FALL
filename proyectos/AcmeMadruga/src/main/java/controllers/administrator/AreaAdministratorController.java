
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("/area/administrator")
public class AreaAdministratorController extends AbstractController {

	public AreaAdministratorController() {
		super();
	}


	//Recursos externos

	@Autowired
	private AreaService	areaService;


	//List------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	ModelAndView list() {
		ModelAndView result;

		Collection<Area> areas;
		areas = this.areaService.findAll();

		result = new ModelAndView("area/list");
		result.addObject("areas", areas);
		result.addObject("requestURI", "area/administrator/list.do");

		return result;
	}

	// Edito mal----------------------------------------------------------------
	@RequestMapping(value = "/editFail", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int areaId, @RequestParam final String message) {
		ModelAndView result;
		Area area;
		try {
			area = this.areaService.findOne(areaId);
			Assert.notNull(area);
			result = this.createEditModelAndView(area);
			result.addObject("message", message);
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		return result;

	}

	// Edito ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int areaId) {
		ModelAndView result;
		Area area;
		if(areaId != 0 && this.areaService.findOneToFail(areaId)== null){
			result = new ModelAndView("security/notfind");
		}else{
		try {
			area = this.areaService.findOne(areaId);
			Assert.notNull(area);
			result = this.createEditModelAndView(area);
		} catch (final Throwable oops) {
			result = new ModelAndView();
		}
		}
		return result;

	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Area area, final BindingResult binding) {
		ModelAndView result;
		if(area.getId() != 0 && this.areaService.findOneToFail(area.getId())== null){
			result = new ModelAndView("security/notfind");
		}else {
		if (binding.hasErrors())
			result = this.createEditModelAndView(area);
		else
			try {
				this.areaService.save(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		}
		return result;
	}

	// Delete -----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET, params = "areaId")
	public ModelAndView delete(@RequestParam final int areaId) {
		/**
		 * Funciona perfectamente. Borra cuando no esta con ningun enrolment y no lo hace cuando no lo está.
		 * Solo 'falla' en que no sé como mostrar el mensaje de error cuando no puede borrar.
		 */
		ModelAndView result;
		final Area a = this.areaService.findOne(areaId);
		try {
			this.areaService.delete(a);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:editFail.do?areaId=" + areaId);
			result.addObject("message", "area.commit.error");
		}
		return result;
	}

	//Create-----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		Area area;
		area = this.areaService.create();

		result = this.createEditModelAndView(area);
		return result;

	}

	//Save create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Area area, final BindingResult binding) {
		ModelAndView result;
		if(area.getId() != 0 && this.areaService.findOneToFail(area.getId())== null){
			result = new ModelAndView("security/notfind");
		}else{
		if (binding.hasErrors())
			result = this.createEditModelAndView(area);
		else
			try {
				this.areaService.save(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView();
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Area area) {
		final ModelAndView result = this.createEditModelAndView(area, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Area area, final String messagecode) {
		ModelAndView result;

		result = new ModelAndView("area/administrator/edit");
		result.addObject("area", area);
		result.addObject("message", messagecode);

		return result;
	}

}
