package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.InvoiceService;
import services.OrderService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Invoice;
import domain.Order;

@Controller
@RequestMapping("/order/company")
public class OrderCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ApplicationService applicationService;

	// Constructors -----------------------------------------------------------

	public OrderCompanyController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Order> orders;

		orders = this.orderService.findOrdersWithApplicationsNotAccepted();

		result = new ModelAndView("order/list");
		result.addObject("orders", orders);
		result.addObject("requestURI", "order/company/list.do");

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int orderId) {
		ModelAndView res;
		Order order = this.orderService.findOneToFail(orderId);
		Company principal = this.companyService.findByPrincipal();
		Invoice invoice;

		invoice = this.invoiceService.findInvoiceByOrderId(orderId);
		Application a = this.applicationService.findAcceptedApplicationByOrderId(orderId);

		if (order == null || a == null) {
			res = new ModelAndView("security/notfind");
		} else if (a.getCompany().getId() != principal.getId()) {
			res = new ModelAndView("security/hacking");
		} else {
			try {
				res = new ModelAndView("order/display");
				res.addObject("order", order);
				res.addObject("invoice", invoice);
			} catch (Throwable oops) {
				res = new ModelAndView("security/notfind");
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/listByCustomer", method = RequestMethod.GET)
	public ModelAndView listByCustomer(@RequestParam int customerId) {
		ModelAndView result;
		Collection<Order> orders;
		
		orders = this.orderService.findOrdersByCustomerId(customerId);

		if (orders == null) {
			result = new ModelAndView("security/notfind");
		}
			
		result = new ModelAndView("order/list");
		result.addObject("orders", orders);
		result.addObject("requestURI", "order/customer/listByCustomer.do");

		return result;
	}

}
