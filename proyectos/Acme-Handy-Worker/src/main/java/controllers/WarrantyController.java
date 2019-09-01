
package controllers;

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

import services.ConfigurationService;
import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("/warranty")
public class WarrantyController extends AbstractController {

	@Autowired
	private WarrantyService	warrantyService;
	
	@Autowired
	private ConfigurationService	configurationService;



	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showPhase(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;
		warranty = this.warrantyService.findWarrantyById(warrantyId);
		result = new ModelAndView("warranty/show");
		result.addObject("warranty", warranty);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Warranty> warranties;

		warranties = this.warrantyService.findAllWarranties();

		result = new ModelAndView("warranty/list");
		result.addObject("warranties", warranties);

		result.addObject("requestURI", "warranty/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.create();
		result = this.createEditModelAndView(warranty);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findWarrantyById(warrantyId);
		Assert.notNull(warranty);
		result = this.createEditModelAndView(warranty);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(warranty);
		else
			//			try {
			this.warrantyService.save(warranty);
		result = new ModelAndView("redirect:list.do");
		//			} catch (final Throwable oops) {
		//				result = this.createEditModelAndView(warranty, "warranty.commit.error");
		//			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		try {
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warranty, "warranty.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty) {
		ModelAndView result;

		result = this.createEditModelAndView(warranty, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("warranty/edit");
		result.addObject("warranty", warranty);
		result.addObject("message", messageCode);
		result.addObject("configuration", configurationService.findConfiguration());
		return result;
	}

}
