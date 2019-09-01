package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.ConfigurationService;
import services.CreditCardService;
import domain.Auditor;
import domain.CreditCard;
import forms.AuditorForm;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	//Constructors ------------------------------------------------------
	
	public AuditorController(){
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(){
		ModelAndView res;
		AuditorForm auditorForm;
		
		String[] makes = this.configurationService.findConfiguration().getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		
		auditorForm = new AuditorForm();
		auditorForm.setCheckTerms(false);
		
		res = new ModelAndView("auditor/register");
		res.addObject("auditorForm", auditorForm);
		res.addObject("creditcardMakes", creditcardMakes);
		
		return res;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView save(AuditorForm form, BindingResult binding){
		ModelAndView res;
		Auditor auditor;
		
		String[] makes =  this.configurationService.findConfiguration().getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		
		auditor = this.auditorService.reconstruct(form, binding);
		CreditCard c = this.creditCardService.reconstruct(form, binding);
		
		if(binding.hasErrors()){
			res = new ModelAndView("auditor/register");
			res.addObject("creditcardMakes", creditcardMakes);
		}else
			try{
				if(auditor.getId() != 0){
					this.auditorService.save(auditor);
				}else{
					this.auditorService.saveFirst(auditor, c);
				}
				res = new ModelAndView("redirect:../");
				res.addObject("message","actor.register.success");
				res.addObject("name", auditor.getName());
				
			}catch(final Throwable oops){
				res = new ModelAndView("auditor/register");
				res.addObject("message", "actor.commit.error");
				res.addObject("creditcardMakes", creditcardMakes);
			}
		return res;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;
		Auditor auditor;
		
		auditor = this.auditorService.findOneTrimmedByPrincipal();
		try{
			res = new ModelAndView("auditor/edit");
			res.addObject("auditor", auditor);
		}catch (final Throwable oops){
			res = this.createEditModelAndView(auditor, "actor.commit.error");
		}
		return res;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public ModelAndView save(Auditor auditor, BindingResult binding){
		ModelAndView res;
		Auditor toSave;
		
		toSave = this.auditorService.reconstruct(auditor, binding);
		
		if(binding.hasErrors()){
			res = new ModelAndView("auditor/edit");
		}else
			try{
				this.auditorService.save(toSave);
				res = new ModelAndView("welcome/index");
				res.addObject("name", toSave.getName());
				res.addObject("exitCode", "actor.edit.success");
			}catch(final Throwable oops){
				res = this.createEditModelAndView(toSave, "actor.commit.error");
			}
		
		return res;
	}
	
	protected ModelAndView createEditModelAndView(final Auditor auditor){
		ModelAndView res = this.createEditModelAndView(auditor,null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Auditor auditor, String messagecode){
		ModelAndView res;
		
		res = new ModelAndView();
		res.addObject("auditor", auditor);
		res.addObject("message", messagecode);
		return res;
		
	}
}
