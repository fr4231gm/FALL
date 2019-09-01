
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FloatService;
import domain.Actor;
import domain.Brotherhood;
import forms.FloatForm;

@Controller
@RequestMapping("/float")
public class FloatController extends AbstractController {

	// Singletons
	@Autowired
	private FloatService	floatService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public FloatController() {
		super();
	}

	// List the procession of a brotherhood
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"brotherhoodId"
	})
	public ModelAndView list(final int brotherhoodId) {

		ModelAndView res;
		Collection<domain.Float> floats;
		boolean permiso = false;
		try{
			final Actor principal = this.actorService.findByPrincipal();
			if (principal instanceof Brotherhood)
				if (principal.getId() == brotherhoodId)
					permiso = true;
	
			floats = this.floatService.findFloatsByBrotherhoodId(brotherhoodId);
	
			res = new ModelAndView("float/list");
			res.addObject("floats", floats);
			res.addObject("permiso", permiso);
			res.addObject("requestURI", "float/list.do?brotherhoodId=" + brotherhoodId);
		}catch(Throwable oops){
			floats = this.floatService.findFloatsByBrotherhoodId(brotherhoodId);
			res = new ModelAndView("float/list");
			res.addObject("floats", floats);
			res.addObject("permiso", permiso);
			res.addObject("requestURI", "float/list.do?brotherhoodId=" + brotherhoodId);
		
		}
		
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"floatId"
	})
	public ModelAndView display(final int floatId) {

		ModelAndView res;
		//Initialize variables
		domain.Float floatt;
		FloatForm floatForm;
		String pictures;

		try {
			floatt = this.floatService.findOne(floatId);
			floatForm = this.floatService.construct(floatt);

			pictures = floatt.getPictures();
			pictures.replace(" ", "");
			final String[] aux = pictures.split("\r\n");

			res = new ModelAndView("float/display");
			res.addObject("float", floatForm);
			res.addObject("picturesList", aux);
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notfind");
		}

		return res;
	}
}
