
package controllers.company;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.OrderService;
import services.PrintSpoolerService;
import services.RequestService;
import controllers.AbstractController;
import domain.Company;
import domain.Order;
import domain.PrintSpooler;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request/company")
public class RequestCompanyController extends AbstractController {

	@Autowired
	private RequestService		requestService;

	@Autowired
	private OrderService		orderService;

	@Autowired
	private PrintSpoolerService	printSpoolerService;

	@Autowired
	private CompanyService		companyService;


	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int orderId) {
		ModelAndView result;

		try {
			final RequestForm res = new RequestForm();
			final Company principal = this.companyService.findByPrincipal();
			final Order order = this.orderService.findOneToFail(orderId);
			Assert.isTrue(this.orderService.findAcceptedApplicationByOrderId(orderId).getCompany().getId() == principal.getId());
			res.setOrder(order);
			res.setStartDate(new Date(System.currentTimeMillis() - 1));
			result = this.createEditModelAndView(res);
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RequestForm requestForm, final BindingResult binding) {
		ModelAndView result;
		final Request request;
		request = this.requestService.reconstruct(requestForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(requestForm);
		else
			try {
				Assert.isNull(this.requestService.findByOrderId(requestForm.getOrder().getId()));
				request.setNumber(this.requestService.findLastNumberOfPrintSpooler(requestForm.getPrintSpooler().getId()) + 1);
				final Request saved = this.requestService.save(request);
				final PrintSpooler ps = this.printSpoolerService.findOne(requestForm.getPrintSpooler().getId());

				final Collection<Request> cola = ps.getRequests();
				cola.add(saved);
				ps.setRequests(cola);

				final PrintSpooler savedps = this.printSpoolerService.save(ps);

				result = new ModelAndView("redirect:/printer/company/display.do?printerId=" + savedps.getPrinter().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(requestForm, "request.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
		try {
			final Request r = this.requestService.findOne(requestId);
			final PrintSpooler ps = this.printSpoolerService.findByRequest(requestId);
			this.printSpoolerService.removeRequest(r, ps);
			this.requestService.delete(r);
			result = new ModelAndView("redirect:/printer/company/display.do?printerId=" + ps.getPrinter().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
			result.addObject("message", "request.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/done", method = RequestMethod.GET)
	public ModelAndView done(@RequestParam final int requestId) {
		ModelAndView result;
		try {
			final Request r = this.requestService.findOne(requestId);
			final PrintSpooler ps = this.printSpoolerService.findByRequest(requestId);
			this.printSpoolerService.doneRequest(r, ps);
			result = new ModelAndView("redirect:/printer/company/display.do?printerId=" + ps.getPrinter().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("security/hacking");
			result.addObject("message", "request.commit.error");
		}
		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final RequestForm sp) {
		ModelAndView result;

		result = this.createEditModelAndView(sp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RequestForm sp, final String messageCode) {
		final ModelAndView result;
		final boolean permission = false;
		Collection<PrintSpooler> printSpoolers;

		printSpoolers = this.printSpoolerService.findByPrincipal();

		result = new ModelAndView("request/edit");
		result.addObject("requestForm", sp);
		result.addObject("permission", permission);
		result.addObject("printSpoolers", printSpoolers);
		result.addObject("message", messageCode);

		return result;

	}

}
