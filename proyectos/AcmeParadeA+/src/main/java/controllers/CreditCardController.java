package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CreditCardService;
import services.SponsorService;
import domain.CreditCard;
import domain.Sponsor;
import forms.CreditCardForm;

@Controller
@RequestMapping("/creditcard/sponsor")
public class CreditCardController extends AbstractController{

	//Constructors -----------------------------------------------
	public CreditCardController(){
		super();
	}

	//Service ----------------------------------------------------
	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private ConfigurationService configService;
	
	@Autowired
	private SponsorService sponsorService;
	
	//List -------------------------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(){

		ModelAndView res;
		Collection<CreditCard> creditcards;

		//Sponsor
		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();
		Calendar fecha = Calendar.getInstance();
		int mes = fecha.get(Calendar.MONTH)+1;
		int anyo = Integer.parseInt(Integer.toString(fecha.get(Calendar.YEAR)).substring(2, 4));
		/*int anyo = fecha.get(Calendar.YEAR);
		String anyo2digits = Integer.toString(anyo);
		String anyoParseado = anyo2digits.substring(2, 3);
		int anyo2 = Integer.parseInt(anyoParseado);*/
		
		Collection<Boolean> isEnabled = new ArrayList<>();
		
		creditcards = this.creditCardService.findCreditCardsBySponsorId(principal.getId());
		
		
		for (CreditCard c : creditcards ){
			if (c.getExpirationYear() > anyo || (c.getExpirationYear()==anyo && c.getExpirationMonth() > mes )){
				isEnabled.add(true);	
			}else{
				isEnabled.add(false);	
			}
		}
		
		res = new ModelAndView("creditcard/sponsor/list");
		res.addObject("creditcards",creditcards);
		res.addObject("requestURI","/creditcard/sponsor/list.do");
		res.addObject("messageError", false);
		res.addObject("mes", mes);
		res.addObject("anyo", anyo);
		res.addObject("isEnabled", isEnabled);
		return res;
	}
	@RequestMapping(value="/listError", method = RequestMethod.GET, params = {"messageError"})
	public ModelAndView listError(boolean messageError){
		ModelAndView res = this.list();
		res.addObject("messageError", messageError);
		return res;
	}
	
	//Display-------------------------------------------------------
	@RequestMapping(value="/display", method = RequestMethod.GET, params = {"creditcardId"})
	public ModelAndView display (final Integer creditcardId){
		ModelAndView res;
		CreditCard cc;
		CreditCardForm ccForm;
		Sponsor principal = this.sponsorService.findByPrincipal();
		try{
			Assert.isTrue(this.creditCardService.findOne(creditcardId).getSponsor() == principal);
			try{
				cc = this.creditCardService.findOne(creditcardId);
				ccForm = this.creditCardService.construct(cc);
				//res = this.createEditModelAndView(ccForm);
				res = new ModelAndView("creditcard/sponsor/display");
				res.addObject("creditCardForm", ccForm);
			}catch(final Throwable oops){
				res = new ModelAndView("security/notfind");
			}
		}catch(final Throwable oops){
			res = new ModelAndView("security/hacking");
		}
		return res;
	}
	//Create -------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		final CreditCardForm ccForm = new CreditCardForm();
		final Sponsor principal = this.sponsorService.findByPrincipal();
		try{
			ccForm.setId(0);
			ccForm.setSponsorId(principal.getId());
			res = this.createEditModelAndView(ccForm);
			res.addObject("creditcardId", ccForm.getId());
			
		}catch (final Throwable oops){
			res = new ModelAndView("security/hacking");
		}
		return res;
	}

	//Edit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit (@RequestParam final Integer creditcardId){
		ModelAndView res;
		
		CreditCard cc;
		final Sponsor principal = this.sponsorService.findByPrincipal();
		try{
			Assert.isTrue(this.creditCardService.findOne(creditcardId).getSponsor().getId()== principal.getId());
			try{
				cc = this.creditCardService.findOne(creditcardId);
				Assert.notNull(cc);
				final CreditCardForm ccForm = this.creditCardService.construct(cc);
				res = this.createEditModelAndView(ccForm);
			}catch (final Throwable oops){
				res = new ModelAndView();
			}
		}catch (final Throwable oops){
			res = new ModelAndView("security/hacking");
		}
		return res;
	}

	//Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save (final CreditCardForm ccForm, final BindingResult binding){
		//Initialize variables
		ModelAndView res;
		CreditCard cc;
		cc = this.creditCardService.reconstruct(ccForm, binding);
		if(binding.hasErrors()){
			res = this.createEditModelAndView(ccForm);
		}else{
			try{
				this.creditCardService.save(cc);
				res = new ModelAndView("redirect:list.do");
			}catch(final Throwable oops){
				res = this.createEditModelAndView(ccForm, "creditcard.commit.error.save");
			}
		}
		return res;
	}
	
	//Delete ------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer creditcardId){
		ModelAndView res;
		
		if(creditcardId != 0 && this.creditCardService.findOneToFail(creditcardId) == null){
			res = new ModelAndView("security/notfind");
		} else {
			
		try{
			Assert.notNull(this.creditCardService.findOne(creditcardId));
			this.creditCardService.delete(this.creditCardService.findOne(creditcardId));
			res = new ModelAndView("redirect:list.do");
			
		}catch(Throwable oops){
			res = new ModelAndView("redirect:listError.do");
			res.addObject("messageError", true);
		}	
	}
		return res;
	}
	
	//Ancillary methods--------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreditCardForm ccf){
		ModelAndView res;
		res = this.createEditModelAndView(ccf,null);
		return res;
	}

	protected ModelAndView createEditModelAndView (final CreditCardForm creditCardForm, final String messageCode){
		ModelAndView res;
		String[] makes =  this.configService.findConfiguration().getMakes().split(",");
		//List<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		res = new ModelAndView ("creditcard/sponsor/edit");
		res.addObject("creditCardForm",creditCardForm);
		res.addObject("message", messageCode);
		res.addObject("creditcardMakes", creditcardMakes);
		return res;
	}
	}
