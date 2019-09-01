package controllers.customer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.FileService;
import services.InvoiceService;
import controllers.AbstractController;
import domain.Customer;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/customer")
public class InvoiceCustomerController extends AbstractController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FileService 	fileService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Invoice> invoices;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		invoices = this.invoiceService.findInvoicesByCustomerId(principal
				.getId());

		res = new ModelAndView("invoice/list");
		res.addObject("invoices", invoices);
		res.addObject("requestURI", "/invoice/customer/list.do");
		res.addObject("permiso", true);

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int invoiceId) {
		ModelAndView result;

		// Initialize variables
		Invoice invoice;
		Customer principal;
		boolean tienepdf = false;
		int fileId = 0;

		principal = this.customerService.findByPrincipal();
		invoice = this.invoiceService.findOne(invoiceId);

		if (invoice == null) {
			result = new ModelAndView("security/notfind");
		} else if (principal.getId() != invoice.getApplication().getOrder()
				.getCustomer().getId())
			result = new ModelAndView("security/hacking");
		else {
			domain.File file = this.fileService.findByInvoice(invoiceId);
			if (file != null){
				fileId= file.getId();
				tienepdf=true;
			}
			result = new ModelAndView("invoice/display");
			result.addObject("invoice", invoice);
			result.addObject("permiso", true);
			result.addObject("tienepdf", tienepdf);
			result.addObject("fileId", fileId);
		}

		return result;
	}

}
