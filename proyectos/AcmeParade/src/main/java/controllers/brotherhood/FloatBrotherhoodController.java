
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.FloatService;
import controllers.AbstractController;
import domain.Brotherhood;
import forms.FloatForm;

@Controller
@RequestMapping("/float/brotherhood")
public class FloatBrotherhoodController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public FloatBrotherhoodController() {
		super();
	}


	// Service ----------------------------------------------------------------
	@Autowired
	private FloatService		floatService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;
		Collection<domain.Float> floats;

		//Brotherhood
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();

		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());
		res = new ModelAndView("float/list");
		res.addObject("floats", floats);
		res.addObject("permiso", true);
		res.addObject("requestURI", "/float/brotherhood/list.do");

		return res;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final FloatForm floatForm = new FloatForm();
		floatForm.setId(0);

		res = new ModelAndView("float/brotherhood/create");
		res.addObject("floatForm", floatForm);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int floatId) {
		ModelAndView res;
		domain.Float floatt;
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		if(floatId != 0 && this.floatService.findOneToFail(floatId) == null){
			res = new ModelAndView("security/notfind");
		} else {
		try {
			//floatt = this.floatService.findOne(floatId);
			Assert.isTrue(this.floatService.findOne(floatId).getBrotherhood() == principal);
			try {
				floatt = this.floatService.findOne(floatId);
				Assert.notNull(floatt);
				final FloatForm floatForm = this.floatService.construct(floatt);
				res = this.createEditModelAndView(floatForm);
			} catch (final Throwable oops) {
				res = new ModelAndView();
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}
		}
		return res;

	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final FloatForm floatForm, final BindingResult binding) {
		//Initialize variables
		ModelAndView res;
		domain.Float floatt;
		if(floatForm.getId() != 0 && this.floatService.findOneToFail(floatForm.getId()) == null){
			res = new ModelAndView("security/notfind");
		} else {
		floatt = this.floatService.reconstruct(floatForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(floatForm);
		else
			try {
				this.floatService.save(floatt);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(floatForm, "float.commit.error.save");
			}
		}
		return res;

	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final domain.Float floatId) {

		ModelAndView res;

		try {
			this.floatService.delete(floatId);
			res = new ModelAndView("redirect:/float/brotherhood/list.do");
		} catch (final Throwable oops) {
			Brotherhood principal;
			principal = this.brotherhoodService.findByPrincipal();
			Collection<domain.Float> floats;
			floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());
			//res = createEditModelAndView(floatId,"float.commit.error");
			res = new ModelAndView("float/list");
			res.addObject("message", "float.commit.error.delete");
			res.addObject("floats", floats);
			res.addObject("permiso", true);
			res.addObject("requestURI", "/float/brotherhood/list.do");

		}
		return res;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final FloatForm floatt) {
		ModelAndView res;
		res = this.createEditModelAndView(floatt, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final FloatForm floatt, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("float/brotherhood/edit");
		res.addObject("floatForm", floatt);
		res.addObject("message", messageCode);

		return res;
	}

}
