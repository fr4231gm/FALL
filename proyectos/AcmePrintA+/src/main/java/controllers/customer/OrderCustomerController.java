
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CreditCardService;
import services.CustomerService;
import services.InvoiceService;
import services.OrderService;
import controllers.AbstractController;
import domain.Customer;
import domain.Invoice;
import domain.Order;

@Controller
@RequestMapping("/order/customer")
public class OrderCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private OrderService			orderService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private InvoiceService			invoiceService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public OrderCustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Order> orders;
		Boolean permiso = true;

		final Customer principal = this.customerService.findByPrincipal();

		orders = this.orderService.findOrdersByCustomerId(principal.getId());

		if (orders == null)
			result = new ModelAndView("security/notfind");

		if (this.creditCardService.findCreditCardByActorId(principal.getId()) == null)
			permiso = false;

		result = new ModelAndView("order/list");
		result.addObject("permiso", permiso);
		result.addObject("orders", orders);
		result.addObject("requestURI", "order/customer/list.do");

		return result;
	}

	// Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int orderId) {
		ModelAndView res;
		final Order order = this.orderService.findOneToFail(orderId);
		final Customer principal = this.customerService.findByPrincipal();
		Invoice invoice;

		invoice = this.invoiceService.findInvoiceByOrderId(orderId);

		if (order == null)
			res = new ModelAndView("security/notfind");
		else if (order.getCustomer().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = new ModelAndView("order/display");
				res.addObject("order", order);
				res.addObject("invoice", invoice);
			} catch (final Throwable oops) {
				res = new ModelAndView("security/notfind");
			}
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer orderId) {
		ModelAndView res;

		final Customer principal = this.customerService.findByPrincipal();
		final Order order = this.orderService.findOneToFail(orderId);

		if (order.getCustomer().getId() != principal.getId())
			res = new ModelAndView("security/hacking");
		else
			try {

				this.orderService.delete(orderId);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {

				Collection<Order> orders;
				orders = this.orderService.findOrdersByCustomerId(principal.getId());

				res = new ModelAndView("order/list");
				res.addObject("message", "order.commit.error.delete");
				res.addObject("orders", orders);
				res.addObject("requestURI", "order/list.do");

			}

		return res;
	}

	@RequestMapping(value = "/createByPost", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int postId) {
		ModelAndView res;
		Order order;

		order = this.orderService.create(postId);

		res = this.createEditModelAndView(order);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Order order;

		order = this.orderService.create();

		res = this.createEditModelAndView(order);

		return res;
	}

	@RequestMapping(value = "/createByPost", method = RequestMethod.POST, params = "save")
	public ModelAndView saveByPost(final Order order, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(order);
		else
			try {
				this.orderService.save(order);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(order, "order.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(final @Valid Order order, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(order);
		else
			try {
				this.orderService.save(order);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(order, "order.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int orderId) {
		ModelAndView res;
		Order order;
		Customer principal;

		order = this.orderService.findOne(orderId);
		principal = this.customerService.findByPrincipal();

		if (principal.getId() != order.getCustomer().getId())
			res = new ModelAndView("security/hacking");
		else
			try {
				res = this.createEditModelAndView(order);
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(order, "order.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Order order, final BindingResult binding) {
		ModelAndView res;
		Order aux;

		if (binding.hasErrors())
			res = this.createEditModelAndView(order);
		else
			try {
				aux = this.orderService.save(order);
				res = new ModelAndView("redirect:list.do");
				res.addObject("order", aux);

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(order, "order.commit.error");
			}
		return res;
	}

	// Ancillary metods

	protected ModelAndView createEditModelAndView(final Order order) {
		ModelAndView result;
		result = this.createEditModelAndView(order, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Order order, final String messageCode) {
		ModelAndView result;

		final String[] materials = this.configurationService.findConfiguration().getMaterials().split(",");

		result = new ModelAndView("order/edit");
		result.addObject("order", order);
		result.addObject("materials", materials);
		result.addObject("message", messageCode);

		return result;
	}

}
