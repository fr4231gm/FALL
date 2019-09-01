/*
 * CustomerController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import controllers.AbstractController;
import domain.Customer;
import forms.CustomerForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Methods ---------------------------------------------------------------

	// Crear un nuevo Customer
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		// Declaracion de variables
		ModelAndView res;
		CustomerForm customerForm;

		// Crear un customerForm e inicializarlo con los terminos y condiciones
		customerForm = new CustomerForm();
		customerForm.setCheckTerms(false);

		res = new ModelAndView("customer/register");
		res.addObject("customerForm", customerForm);

		return res;
	}

	// Guardar un nuevo Customer
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CustomerForm customerForm,
			final BindingResult binding) {

		// Declaracion de variables
		ModelAndView res;
		Customer customer;

		// Crear el objeto Customer a partir del customerForm
		customer = this.customerService.reconstruct(customerForm, binding);

		// Si el formulario tiene errores se muestran al usuario
		if (binding.hasErrors()) {
			res = new ModelAndView("customer/register");
			// Si el formulario no tiene errores intenta guardarse
		} else {
			try {
				this.customerService.save(customer);

				res = new ModelAndView("redirect:../");
				res.addObject("message", "actor.register.success");
				res.addObject("name", customer.getName());
			} catch (final Throwable opps) {
				res = new ModelAndView("customer/register");
				res.addObject("message", "actor.commit.error");
			}
		}
		return res;
	}

	// Editar customer existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Customer customer;

		customer = this.customerService.findOneTrimmedByPrincipal();

		try {
			result = new ModelAndView("customer/edit");
			result.addObject("customer", customer);

		} catch (final Throwable oops) {
			result = this
					.createEditModelAndView(customer, "actor.commit.error");
		}

		return result;
	}

	// Actualizar customer existente
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	ModelAndView save(final Customer customer, final BindingResult binding) {
		ModelAndView result;
		Customer toSave;

		toSave = this.customerService.reconstruct(customer, binding);

		if (binding.hasErrors())
			result = new ModelAndView("customer/edit");
		else
			try {
				this.customerService.save(toSave);
				result = new ModelAndView("welcome/index");

				result.addObject("name", toSave.getName());
				result.addObject("exitCode", "actor.edit.success");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(toSave,
						"actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = { "customerId" })
	public ModelAndView display(@RequestParam final int customerId) {
		ModelAndView result;

		// Initialize variables
		Customer customer;

		customer = this.customerService.findOne(customerId);

		result = new ModelAndView("customer/display");
		result.addObject("customer", customer);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer) {
		final ModelAndView result = this.createEditModelAndView(customer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView();

		result.addObject("customer", customer);
		result.addObject("message", messagecode);

		return result;
	}
}
