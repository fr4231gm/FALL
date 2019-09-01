
package controllers.provider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Charge;
import domain.Invoice;
import domain.Provider;

@Controller
@RequestMapping("/invoice/provider")
public class InvoiceProviderController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public InvoiceProviderController() {
		super();
	}


	// Service ----------------------------------------------------------------

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private InvoiceService	invoiceService;


	// List --------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int sponsorshipId, final int principalId) {
		ModelAndView res;
		Collection<Charge> charges;
		Invoice invoice;
		Provider principal;
		Integer visits;
		Double sumTotal;
		Double totalWithTax;

		// Provider
		try {

			principal = this.providerService.findByPrincipal();
			Assert.isTrue(principal.getId() == principalId);

			invoice = this.invoiceService.findInvoiceBySponsorshipId(sponsorshipId);
			if (invoice == null){
				res = new ModelAndView("invoice/provider/list");
				visits = 0;
				sumTotal = 0.;
				totalWithTax = 0.;
				res.addObject("visits", visits);
				res.addObject("sumTotal", sumTotal);
				res.addObject("totalWithTax", totalWithTax);
			}else {
				charges = invoice.getCharges();

				visits = this.invoiceService.findVisitsBySponsorshipId(sponsorshipId);
				sumTotal = this.invoiceService.sumTotalAmountInvoiceBySponsorshipId(sponsorshipId);
				totalWithTax = this.invoiceService.totalAmountInvoiceBySponsorship(sponsorshipId);

				res = new ModelAndView("invoice/provider/list");
				res.addObject("charges", charges);
				res.addObject("visits", visits);
				res.addObject("sumTotal", sumTotal);
				res.addObject("totalWithTax", totalWithTax);
				res.addObject("requestURI", "/invoice/provider/list.do");
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("security/notfind");
		}

		return res;
	}

}
