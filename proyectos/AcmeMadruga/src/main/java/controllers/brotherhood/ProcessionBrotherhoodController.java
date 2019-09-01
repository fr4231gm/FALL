
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
import services.ProcessionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Procession;
import forms.ProcessionForm;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	// AQUI VAN LOS SERVICIOS QUE NOS HAGA FALTA
	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;
	
	@Autowired
	private FloatService	floatService;


	// Constructors -----------------------------------------------------------

	public ProcessionBrotherhoodController() {
		super();
	}

	// LIST

	@RequestMapping("/list")
	public ModelAndView list() {

		ModelAndView res;
		Collection<Procession> processions;
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		processions = this.processionService.findProcessionsByBrotherhoodId(principal.getId());

		res = new ModelAndView("procession/list");
		res.addObject("processions", processions);
		res.addObject("requestURI", "/procession/brotherhood/list.do");
		res.addObject("permiso", true);
		return res;
	}

	// DISPLAY

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
		"processionId"
	})
	public ModelAndView display(final int processionId) {
		ModelAndView res;
		
//		if(processionId != 0 && this.processionService.findOneToFail(processionId)== null ){
//			res = new ModelAndView("security/notfind");
//		}else{
		Procession procession;

		procession = this.processionService.findOne(processionId);

		res = new ModelAndView("procession/display");
		res.addObject("procession", procession);
		//}
		return res;
	}

	// CREATE

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Brotherhood principal = this.brotherhoodService.findByPrincipal();
		ModelAndView res;
		ProcessionForm processionForm = new ProcessionForm();
		Collection<domain.Float> floats;
		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());
		
		res = new ModelAndView("procession/brotherhood/create");
		res.addObject("processionForm", processionForm);
		res.addObject("floats", floats);
		
		return res;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int processionId) {
		ModelAndView res;
		Brotherhood principal= this.brotherhoodService.findByPrincipal();
		ProcessionForm processionForm;
		Collection<domain.Float> floats;
		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());
		if(processionId != 0 && this.processionService.findOneToFail(processionId)== null){
			res = new ModelAndView("security/notfind");
		}else {
		try{
			Procession procession = this.processionService.findOne(processionId);
			Assert.isTrue(procession.getBrotherhood() == principal && procession.getisDraft());
		
			try {
				processionForm = this.processionService.construct(this.processionService.findOne(processionId));
				res = this.createEditModelAndView(processionForm);
				res.addObject("floats", floats);
			} catch (final Throwable oops) {
				res = new ModelAndView();	
			}
		}catch (final Throwable oops){
			res = new ModelAndView("security/hacking");
		}
		}
		return res;
	}

	// SAVE

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = {
		"save"
	})
	public ModelAndView save(final ProcessionForm processionForm, final BindingResult binding) {

		ModelAndView result;
		String messageCode;
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();
		if(processionForm.getId() != 0 && this.processionService.findOneToFail(processionForm.getId()) == null){
			result = new ModelAndView("security/notfind");
		} else {
		Procession toSave = this.processionService.reconstruct(processionForm, binding);
		Collection<domain.Float> floats;
		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());

		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("floats", floats);
			result.addObject("bind", binding);
		} else {
			try{
			this.processionService.save(toSave);
			result = new ModelAndView("redirect:/procession/brotherhood/list.do");
			messageCode = "procession.success";
		} catch (final Throwable oops) {
			result = new ModelAndView();

			messageCode = "procession.commit.error";
			result.addObject("message", messageCode);
		}
		}
		// Adding elements to the view
		
		result.addObject("floats", floats);
		}
		return result;
	}

	//Save create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final ProcessionForm processionForm, final BindingResult binding) {
		ModelAndView result;
		String messageCode;
		Brotherhood principal;
		principal = this.brotherhoodService.findByPrincipal();
		
		if(processionForm.getId() != 0 && this.processionService.findOneToFail(processionForm.getId()) == null){
			result = new ModelAndView("security/notfind");
		} else {
		Procession toSave = this.processionService.reconstruct(processionForm, binding);
		Collection<domain.Float> floats;
		floats = this.floatService.findFloatsByBrotherhoodId(principal.getId());

		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("floats", floats);
			result.addObject("bind", binding);
		} else {

		try {
			this.processionService.save(toSave);
			result = new ModelAndView("redirect:/procession/brotherhood/list.do");
			messageCode = "procession.success";
		} catch (final Throwable oops) {
			result = new ModelAndView();
			messageCode = "procession.commit.error";
			result.addObject("floats", floats);
			result.addObject("message", messageCode);
		}
		}
		// Adding elements to the view
		}

		return result;
	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Procession processionId) {

		ModelAndView res;

		// Create the ModelAndView

		try {

			this.processionService.delete(processionId);
			res = new ModelAndView("redirect:/procession/brotherhood/list.do");

		} catch (final Throwable oops) {
			Collection<Procession> processions;
			Brotherhood principal;

			principal = this.brotherhoodService.findByPrincipal();
			processions = this.processionService.findProcessionsByBrotherhoodId(principal.getId());

			res = new ModelAndView("procession/list");
			res.addObject("processions", processions);
			res.addObject("requestURI", "/procession/brotherhood/list.do");
		}
		return res;

	}

	// Ancillary metods

	protected ModelAndView createEditModelAndView(final ProcessionForm procession) {
		ModelAndView result;
		result = this.createEditModelAndView(procession, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProcessionForm procession, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("procession/brotherhood/edit");
		result.addObject("processionForm", procession);
		result.addObject("message", messageCode);

		return result;
	}


}
