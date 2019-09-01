
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Chapter;
import services.ActorService;
import services.ProclaimService;

@Controller
@RequestMapping("/proclaim")
public class ProclaimController extends AbstractController {

	// Supporting services

	@Autowired
	private ProclaimService	proclaimService;

	@Autowired
	private ActorService actorService;


	// Constructor 

	public ProclaimController() {
		super();
	}

	// An actor who is not authenticated must be able to browse the proclaims of the chapters
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int chapterId) {

		ModelAndView res;
		Collection<domain.Proclaim> proclaims;
		boolean permiso = false;
		try {
			final Actor principal = this.actorService.findByPrincipal();
			if (principal instanceof Chapter)
				if (principal.getId() == chapterId)
					permiso = true;

			proclaims = this.proclaimService.findProclaimsByChapterId(chapterId);

			res = new ModelAndView("proclaim/list");
			res.addObject("proclaims", proclaims);
			res.addObject("permiso", permiso);
			res.addObject("requestURI", "proclaim/list.do?chapterId=" + chapterId);
		} catch (Throwable oops) {
			proclaims = this.proclaimService.findProclaimsByChapterId(chapterId);
			res = new ModelAndView("proclaim/list");
			res.addObject("proclaims", proclaims);
			res.addObject("permiso", permiso);
			res.addObject("requestURI", "proclaim/list.do?chapterId=" + chapterId);

		}

		return res;
	}

}
