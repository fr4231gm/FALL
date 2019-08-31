package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Quolet;

import services.QuoletService;

@Controller
@RequestMapping("/quolet")
public class QuoletController extends AbstractController {

	@Autowired
	private QuoletService quoletService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int quoletId) {
		ModelAndView res;
		Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);

		res = new ModelAndView("quolet/display");
		res.addObject("quolet", quolet);
		res.addObject("conference", quolet.getConference());

		return res;
	}
}
