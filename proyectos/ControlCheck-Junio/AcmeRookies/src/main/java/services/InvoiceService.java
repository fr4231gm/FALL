
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.InvoiceRepository;
import domain.Charge;
import domain.Invoice;
import domain.Sponsorship;

@Service
@Transactional
public class InvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private InvoiceRepository	invoiceRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public InvoiceService() {
		super();
	}

	// Simple CRUDs methods ---------------------------------------------------
	public Invoice create(final Sponsorship sponsorship) {
		final Invoice res = new Invoice();

		res.setSponsorship(sponsorship);
		res.setCharges(new ArrayList<Charge>());

		return res;
	}

	//SAVE --------------------------------------------------------------------
	public Invoice save(final Invoice invoice) {

		// Initialize
		Invoice res = invoice;

		// Choose save or update
		if (invoice.getId() == 0)
			res = this.invoiceRepository.save(invoice);
		else {//update Collection<Charge>
			res.setCharges(invoice.getCharges());
			res = this.invoiceRepository.save(invoice);
		}

		return res;
	}

	// FINDONE
	public Invoice findOne(final int invoiceId) {
		Invoice res;
		res = this.invoiceRepository.findOne(invoiceId);
		return res;
	}

	//FLUSH
	public void flush() {
		this.invoiceRepository.flush();
	}

	// Other business methods -------------------------------------------------

	// Find Invoice by SponsorshipId
	public Invoice findInvoiceBySponsorshipId(final int sponsorshipId) {
		Invoice res;
		res = this.invoiceRepository.findInvoiceBySponsorshipId(sponsorshipId);

		return res;
	}

	// Find Charges by SponsorshipId
	public Collection<Charge> findChargesBySponsorshipId(final int sponsorshipId) {
		Collection<Charge> res = new ArrayList<>();
		res = this.invoiceRepository.findChargesBySponsorshipId(sponsorshipId);
		return res;
	}

	// Find number of visits by Sponsorship
	public Integer findVisitsBySponsorshipId(final int sponsorshipId) {
		Integer res;
		res = this.invoiceRepository.findVisitsBySponsorshipId(sponsorshipId);
		return res;
	}

	// Sum of amount of all charges of an invoice by SponsorshipId
	public Double sumTotalAmountInvoiceBySponsorshipId(final int sponsorshipId) {
		Double res;
		res = this.invoiceRepository.sumTotalAmountInvoiceBySponsorshipId(sponsorshipId);
		return res;
	}

	// TotalAmount of charges taking tax into account
	public Double totalAmountInvoiceBySponsorship(final int sponsorshipId) {
		Double res;
		res = this.invoiceRepository.totalAmountInvoiceBySponsorship(sponsorshipId);
		return res;
	}

	public void deleteByUserDropOut(Invoice invoiceToDelete) {
		this.invoiceRepository.delete(invoiceToDelete);		
	}

}
