
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController extends AbstractController {

	//Services
	@Autowired
	private ConfigurationService	configurationService;


	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();
		Assert.notNull(configuration);
		result = this.createEditModelAndView(configuration);
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(configuration);
			result.addObject("bind", binding);
		} else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:edit.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, "configuration.save.error");
				oops.printStackTrace();
			}
		return result;
	}

	//Ancillary method
	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;
		result = this.createEditModelAndView(configuration, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", messageCode);
		result.addObject("configuration", configurationService.findConfiguration());
		return result;
	}
}
