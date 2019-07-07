
package controllers.administrator;

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
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

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
		final Configuration config2 = this.configurationService.findOtherConfiguration();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(configuration);
			result.addObject("bind", binding);
		} else
			try {
				config2.setBanner(configuration.getBanner());
				this.configurationService.save(configuration);
				this.configurationService.save(config2);
				result = new ModelAndView("redirect:/");

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
		return result;
	}
}
