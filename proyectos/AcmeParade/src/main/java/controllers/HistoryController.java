
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import domain.History;

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {

	// Services

	@Autowired
	private HistoryService	historyService;


	// Constructors

	public HistoryController() {
		super();
	}

	// Listing

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int historyId) {
		final ModelAndView result;
		History history;

		history = this.historyService.findOne(historyId);

		result = new ModelAndView("history/display");
		result.addObject("history", history);
		result.addObject("principal", null);
		return result;

	}

}
