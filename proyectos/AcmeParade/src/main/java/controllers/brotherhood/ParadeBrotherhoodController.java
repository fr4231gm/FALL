
package controllers.brotherhood;

import java.util.ArrayList;
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
import services.ParadeService;
import services.SegmentService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;
import forms.ParadeForm;

@Controller
@RequestMapping("/parade/brotherhood")
public class ParadeBrotherhoodController extends AbstractController {

	// AQUI VAN LOS SERVICIOS QUE NOS HAGA FALTA
	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private FloatService		floatService;

	@Autowired
	private SegmentService		segmentService;


	// Constructors -----------------------------------------------------------

	public ParadeBrotherhoodController() {
		super();
	}

	// LIST

	@RequestMapping("/list")
	public ModelAndView list() {

		ModelAndView res;
		Collection<Parade> parades;

		res = new ModelAndView("parade/list");
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		parades = this.paradeService.findParadesByBrotherhoodId(principal.getId());

		res.addObject("requestURI", "/parade/brotherhood/list.do");
		res.addObject("parades", parades);
		res.addObject("permiso", true);

		return res;
	}

	// DISPLAY

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"paradeId"
	})
	public ModelAndView display(final int paradeId) {
		ModelAndView res;

		//		if(paradeId != 0 && this.paradeService.findOneToFail(paradeId)== null ){
		//			res = new ModelAndView("security/notfind");
		//		}else{
		Parade parade;

		parade = this.paradeService.findOne(paradeId);

		res = new ModelAndView("parade/display");
		res.addObject("parade", parade);
		//}
		return res;
	}

	// CREATE

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		ModelAndView res;
		final ParadeForm paradeForm = new ParadeForm();
		Collection<domain.Float> floats;
		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());

		res = new ModelAndView("parade/brotherhood/create");
		res.addObject("paradeForm", paradeForm);
		res.addObject("floats", floats);

		return res;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int paradeId) {
		ModelAndView res;
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		ParadeForm paradeForm;

		Collection<domain.Float> floats;
		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());
		if (paradeId != 0 && this.paradeService.findOneToFail(paradeId) == null)
			res = new ModelAndView("security/notfind");
		else
			try {
				final Parade parade = this.paradeService.findOne(paradeId);
				Assert.isTrue(parade.getBrotherhood() == principal && parade.getisDraft());

				try {
					paradeForm = this.paradeService.construct(this.paradeService.findOne(paradeId));
					res = this.createEditModelAndView(paradeForm);
					res.addObject("floats", floats);
				} catch (final Throwable oops) {
					res = new ModelAndView();
				}
			} catch (final Throwable oops) {
				res = new ModelAndView("security/hacking");
			}
		return res;
	}

	// SAVE

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = {
		"save"
	})
	public ModelAndView save(final ParadeForm paradeForm, final BindingResult binding) {

		ModelAndView result;
		String messageCode;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		if (paradeForm.getId() != 0 && this.paradeService.findOneToFail(paradeForm.getId()) == null)
			result = new ModelAndView("security/notfind");
		else {
			final Parade toSave = this.paradeService.reconstruct(paradeForm, binding);
			Collection<domain.Float> floats;
			floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());

			if (binding.hasErrors()) {
				result = new ModelAndView();
				result.addObject("floats", floats);
				result.addObject("bind", binding);
			} else
				try {
					if (paradeForm.getisDraft() == false)
						toSave.setStatus("SUBMITTED");

					this.paradeService.save(toSave);
					result = new ModelAndView("redirect:/parade/brotherhood/list.do");
					messageCode = "parade.success";
				} catch (final Throwable oops) {
					result = new ModelAndView();

					messageCode = "parade.commit.error";
					result.addObject("message", messageCode);
				}

			result.addObject("floats", floats);
		}
		return result;
	}

	//Save create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final ParadeForm paradeForm, final BindingResult binding) {
		ModelAndView result;
		String messageCode;
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();

		if (paradeForm.getId() != 0 && this.paradeService.findOneToFail(paradeForm.getId()) == null)
			result = new ModelAndView("security/notfind");
		else {
			final Parade toSave = this.paradeService.reconstruct(paradeForm, binding);

			Collection<domain.Float> floats;
			floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());

			if (binding.hasErrors()) {
				result = new ModelAndView();
				result.addObject("floats", floats);
				result.addObject("bind", binding);
			} else
				try {
					toSave.setRejectReason("");
					toSave.setStatus("");
					if (!paradeForm.getisDraft())
						toSave.setStatus("SUBMITTED");
					this.paradeService.save(toSave);
					result = new ModelAndView("redirect:/parade/brotherhood/list.do");
					messageCode = "parade.success";
				} catch (final Throwable oops) {
					result = new ModelAndView();
					messageCode = "parade.commit.error";
					result.addObject("floats", floats);
					result.addObject("message", messageCode);
				}
		}

		return result;
	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Parade paradeId) {

		ModelAndView res;

		// Create the ModelAndView

		try {

			this.paradeService.delete(paradeId);
			res = new ModelAndView("redirect:/parade/brotherhood/list.do");

		} catch (final Throwable oops) {
			Collection<Parade> parades;
			Brotherhood principal;

			principal = this.brotherhoodService.findByPrincipal();
			parades = this.paradeService.findParadesByBrotherhoodId(principal.getId());

			res = new ModelAndView("parade/list");
			res.addObject("parades", parades);
			res.addObject("requestURI", "/parade/brotherhood/list.do");
		}
		return res;

	}

	/*
	 * //COPY
	 * 
	 * @RequestMapping(value = "/copy", method = RequestMethod.GET)
	 * public ModelAndView copy(final Parade parade, final BindingResult binding) {
	 * ModelAndView res;
	 * 
	 * return res;
	 * }
	 */

	// EDIT
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView copy(@RequestParam final int paradeId) {
		ModelAndView res;
		Parade parade = this.paradeService.findOne(paradeId);
		Assert.isTrue(this.paradeService.findOne(paradeId).getBrotherhood().equals(this.brotherhoodService.findOneTrimmedByPrincipal()));
		final Parade paradex = parade;
		final Collection<String> l = new ArrayList<String>();
		final Collection<Segment> s = new ArrayList<Segment>();
		final Collection<domain.Float> f = new ArrayList<domain.Float>();
		if (parade.getId() != 0 && this.paradeService.findOneToFail(parade.getId()) == null)
			res = new ModelAndView("security/notfind");
		else
			try {
				final Parade paradeNew = this.paradeService.copy(parade);
				parade = this.paradeService.save(paradeNew);
				res = new ModelAndView("redirect:/parade/brotherhood/list.do");
				l.addAll(paradex.getLocations());
				for (final Segment ss : paradex.getSegments()) {
					final Segment xs = this.segmentService.create();
					xs.setDestination(ss.getDestination());
					xs.setOrigin(ss.getOrigin());
					xs.setEndTime(ss.getEndTime());
					xs.setStartTime(ss.getStartTime());
					this.segmentService.save(xs);
					s.add(xs);
				}
				f.addAll(paradex.getFloats());
				parade.setFloats(f);
				parade.setSegments(s);
				parade.setLocations(l);
				this.paradeService.save(parade);
				res.addObject("parade", parade);
				res.addObject("paradeNew", paradeNew);
			} catch (final Throwable oops) {
				res = new ModelAndView();
			}

		return res;
	}
	//SAVE COPY
	@RequestMapping(value = "/copy", method = RequestMethod.POST, params = "copy")
	public ModelAndView saveCopy(final Parade parade, final BindingResult binding) {
		ModelAndView res;
		String messageCode;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		if (parade.getId() != 0 && this.paradeService.findOneToFail(parade.getId()) == null)
			res = new ModelAndView("security/notfind");
		else {
			final Parade toSave = this.paradeService.copy(parade);
			Collection<domain.Float> floats;
			floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());

			if (binding.hasErrors()) {
				res = new ModelAndView();
				res.addObject("floats", floats);
				res.addObject("bind", binding);
			} else
				try {
					this.paradeService.save(toSave);
					res = new ModelAndView("redirect:/parade/brotherhood/list.do");
					messageCode = "parade.success";
				} catch (final Throwable oops) {
					res = new ModelAndView();

					messageCode = "parade.commit.error";
					res.addObject("message", messageCode);
				}

			res.addObject("floats", floats);
		}
		return res;
	}

	// Ancillary metods

	protected ModelAndView createEditModelAndView(final ParadeForm parade) {
		ModelAndView result;
		result = this.createEditModelAndView(parade, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ParadeForm parade, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("parade/brotherhood/edit");
		result.addObject("paradeForm", parade);
		result.addObject("message", messageCode);

		return result;
	}

}
