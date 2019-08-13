package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.PanelService;
import domain.Conference;
import domain.Panel;

@Controller
@RequestMapping("/panel")
public class PanelController extends AbstractController {

	@Autowired
	private PanelService panelService;
	
	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView res;
		Panel panel;

		panel = this.panelService.create(conferenceId);
		Assert.notNull(panel);
		res = this.createEditModelAndView(panel);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Panel panel, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(panel);
		} else {
			try {
				this.panelService.save(panel);
				res = new ModelAndView("panel/display");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(panel,
						"activity.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int panelId) {
		ModelAndView res;
		Panel panel;
		
		panel = this.panelService.findOne(panelId);

		res = new ModelAndView("panel/display");
		res.addObject("panel", panel);
		res.addObject("schedule", this.activityService.getSchedule(panel));

		return res;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int panelId) {

		ModelAndView result;
		Panel panel;
		Conference conference;
		
		panel = this.panelService.findOne(panelId);
		conference = panel.getConference();

		try {
			this.panelService.delete(panel);
			result = new ModelAndView("redirect:/activity/list.do?conferenceId="+conference.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(panel, "activity.commit.error");
		}

		return result;

	}


	// Ancilliary methods

	protected ModelAndView createEditModelAndView(final Panel panel) {
		final ModelAndView result = this.createEditModelAndView(panel, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Panel panel,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("panel/edit");

		result.addObject("panel", panel);
		result.addObject("message", messagecode);

		return result;
	}
}
