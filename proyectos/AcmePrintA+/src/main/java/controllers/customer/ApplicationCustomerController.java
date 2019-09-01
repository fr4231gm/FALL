
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.InvoiceService;
import services.OrderService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;
import domain.Invoice;
import domain.Order;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private OrderService		orderService;

	@Autowired
	private InvoiceService		invoiceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listByOrder(@RequestParam final int orderId) {
		ModelAndView res;
		final Collection<Application> applications;
		Customer principal;
		Order order;

		principal = this.customerService.findByPrincipal();
		order = this.orderService.findOne(orderId);
		applications = this.applicationService.findApplicationsByOrderAndCustomer(orderId, principal.getId());

		if (principal.getId() != order.getCustomer().getId())
			res = new ModelAndView("security/hacking");
		else {
			res = new ModelAndView("application/list");
			res.addObject("applications", applications);
			res.addObject("requestURI", "/application/customer/list.do?orderId=" + orderId);
			res.addObject("permiso", true);
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView res;
		Application application;
		Customer principal;

		application = this.applicationService.findOne(applicationId);
		principal = this.customerService.findByPrincipal();

		if (principal.getId() != application.getOrder().getCustomer().getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = this.createEditModelAndView(application);
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(application, "application.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView res;
		Application aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(application);
		else
			try {
				aux = this.applicationService.save(application);
				res = new ModelAndView("redirect:display.do?applicationId=" + aux.getId());
				res.addObject("application", aux);

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(application, "application.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;

		// Initialize variables
		Application application;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);

		Invoice invoice;

		invoice = this.invoiceService.findInvoiceByApplicationId(applicationId);

		if (principal.getId() != application.getOrder().getCustomer().getId())
			result = new ModelAndView("security/hacking");
		else {
			result = new ModelAndView("application/display");
			result.addObject("application", application);
			result.addObject("permiso", true);
			result.addObject("invoice", invoice);
		}

		return result;
	}

	// Accept an application

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView res;
		Application application;
		final Customer principal = this.customerService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);

//		try {
//			Assert.isTrue(application.getOrder().getCustomer().equals(principal));
//		try {

				this.applicationService.accept(application);
				res = new ModelAndView("application/comment");
				res.addObject("application", application);
				res.addObject("permiso", true);

//			} catch (final Throwable oops) {
//				res = new ModelAndView("redirect:/application/customer/list.do?orderId=" + application.getOrder().getId());
//				res.addObject("message", "application.accept.error");
//			}
//		} catch (final Throwable oops) {
//			res = new ModelAndView("security/hacking");
//		}

		return res;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = "accept")
	public ModelAndView commentAccept(@Valid final Application application, final BindingResult binding) {
		ModelAndView res;
		Application aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(application);
		else
			try {
				aux = this.applicationService.updateStatus(application);
				res = new ModelAndView("redirect:display.do?applicationId=" + aux.getId());

			} catch (final Throwable oops) {
				System.out.println("Catch");
				res = new ModelAndView("application/comment");
				res.addObject("message", "application.commit.error");
			}
		return res;
	}

	// Reject an application

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView res;
		Application application, rejected;
		final Customer principal = this.customerService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);

		try {
			Assert.isTrue(application.getOrder().getCustomer().equals(principal));
			try {

				rejected = this.applicationService.reject(application);
				res = new ModelAndView("application/comment");
				res.addObject("application", rejected);
				res.addObject("permiso", true);

			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/application/customer/list.do?orderId=" + application.getOrder().getId());
				res.addObject("message", "application.reject.error");
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/hacking");
		}

		return res;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "reject")
	public ModelAndView commentReject(@Valid final Application application, final BindingResult binding) {
		ModelAndView res;
		Application aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(application);
		else
			try {
				aux = this.applicationService.updateStatus(application);
				res = new ModelAndView("redirect:display.do?applicationId=" + aux.getId());

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(application, "application.commit.error");
			}
		return res;
	}

	// Ancillary metods

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;
		result = this.createEditModelAndView(application, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}

}
