package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Company;
import domain.CreditCard;

import forms.CompanyForm;

import services.CompanyService;
import services.ConfigurationService;
import services.CreditCardService;
import services.PositionService;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private CreditCardService creditCardService;

	// Constructor -------------------------------------

	public CompanyController() {
		super();
	}

	// Crear una nueva Company
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		CompanyForm companyForm;

		String[] makes =  this.configurationService.findConfiguration().getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		
		// Crear un companyForm e inicializarlo con los terminos y condiciones
		companyForm = new CompanyForm();
		companyForm.setCheckTerms(false);

		res = new ModelAndView("company/register");
		res.addObject("companyForm", companyForm);
		res.addObject("creditcardMakes", creditcardMakes);

		return res;
	}

	// Guardar una nueva Company
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CompanyForm companyForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Company company;
		
		String[] makes =  this.configurationService.findConfiguration().getMakes().split(",");
		Collection<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		// Crear el objeto Company a partir del companyForm
		company = this.companyService.reconstruct(companyForm, binding);
		CreditCard c = this.creditCardService.reconstruct(companyForm, binding);
		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()){
			res = new ModelAndView("company/register");
			res.addObject("creditcardMakes", creditcardMakes);
		// Si el formulario no tiene errores intenta guardarse
		}else
			try {
				if(company.getId()!=0){
				this.companyService.save(company);
				}else{
				this.companyService.saveFirst(company, c);
				}
				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", company.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("company/register");
				res.addObject("message", "actor.commit.error");
				res.addObject("creditcardMakes", creditcardMakes);
			}
		return res;
	}

	// Editar company existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Company company;

		company = this.companyService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("company/edit");
			result.addObject("company", company);

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(company, "actor.commit.error");
		}

		return result;
	}

	// Actualizar company existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Company company, final BindingResult binding) {
		ModelAndView result;
		Company toSave;

		toSave = this.companyService.reconstruct(company, binding);

		if (binding.hasErrors())
			result = new ModelAndView("company/edit");
		else
			try {
				this.companyService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	// RF 7.3:List the companies available and navigate to the corresponding
	// positions.

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Company> companies;

		companies = this.companyService.findAll();

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "positionId" })
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;

		// Initialize variables
		Company company;

		company = this.positionService.findOne(positionId).getCompany();

		result = new ModelAndView("company/display");
		result.addObject("company", company);
		result.addObject("requestURI", "company/display.do?positionId="
				+ positionId);

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "companyId" })
	public ModelAndView display2(@RequestParam final int companyId) {
		ModelAndView result;

		// Initialize variables
		Company company;

		company = this.companyService.findOne(companyId);

		result = new ModelAndView("company/display");
		result.addObject("company", company);
		result.addObject("requestURI", "company/display.do?companyId="
				+ companyId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Company company) {
		final ModelAndView result = this.createEditModelAndView(company, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Company company,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("company", company);
		result.addObject("message", messagecode);

		return result;
	}

}
