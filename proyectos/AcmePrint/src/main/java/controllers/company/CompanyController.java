
package controllers.company;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import controllers.AbstractController;
import domain.Company;
import forms.CompanyForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Constructor ------------------------------------------------------------

	public CompanyController() {
		super();
	}

	// Crear una nueva Company
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		CompanyForm companyForm;

		// Crear un companyForm e inicializarlo con los terminos y condiciones
		companyForm = new CompanyForm();
		companyForm.setCheckTerms(false);

		res = new ModelAndView("company/register");
		res.addObject("companyForm", companyForm);

		return res;
	}

	// Guardar una nueva Company
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CompanyForm companyForm, final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Company company;

		// Crear el objeto Company a partir del companyForm
		company = this.companyService.reconstruct(companyForm, binding);
		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()){
			res = new ModelAndView("company/register");
		// Si el formulario no tiene errores intenta guardarse
		}else{
			try {

				this.companyService.save(company);

				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", company.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("company/register");
				res.addObject("message", "actor.commit.error");
			}
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
				result = this.createEditModelAndView(toSave, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Company> companies;

		companies = this.companyService.findCompaniesAvailableToAttendOrders();

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET, params = {
			"companyId"
		})
		public ModelAndView display(@RequestParam final int companyId) {
			ModelAndView result;

			// Initialize variables
			Company company;

			company = this.companyService.findOne(companyId);

			result = new ModelAndView("company/display");
			result.addObject("company", company);

			return result;
		}

	protected ModelAndView createEditModelAndView(final Company company) {
		final ModelAndView result = this.createEditModelAndView(company, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Company company, final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("company", company);
		result.addObject("message", messagecode);

		return result;
	}

}
