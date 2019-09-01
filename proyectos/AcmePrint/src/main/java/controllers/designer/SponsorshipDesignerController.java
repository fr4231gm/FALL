
package controllers.designer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/designer")
public class SponsorshipDesignerController extends AbstractController {

	//Constructor -----------------------------------------------------
	public SponsorshipDesignerController() {
		super();
	}


	//Service--------------------------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;


	//List
	@RequestMapping(value = "/listDesigner", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByDesignerId();

		res = new ModelAndView("sponsorship/listDesigner");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsorship/designer/listDesigner.do");

		return res;

	}
}
