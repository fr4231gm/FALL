package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Charge;
import domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

	// Find Invoice by SponsorshipId
	@Query("select i from Invoice i where i.sponsorship.id = ?1")
	Invoice findInvoiceBySponsorshipId(int id);
	
	// Find Charges by SponsorshipId
	@Query("select i.charges from Invoice i where i.sponsorship.id = ?1")
	Collection<Charge> findChargesBySponsorshipId(int id);
	
	// Find number of visits by Sponsorship
	@Query("select i.charges.size from Invoice i where i.sponsorship.id = ?1")
	Integer findVisitsBySponsorshipId(int id);
	
	// Sum of amount of all charges of an invoice by SponsorshipId
	@Query("select sum(c.amount) from Invoice i JOIN i.charges c where i.sponsorship.id = ?1")
	Double sumTotalAmountInvoiceBySponsorshipId(int id);
	
	// TotalAmount of charges taking tax into account
	@Query("select sum(c.amount*(1+(c.tax))) from Invoice i JOIN i.charges c where i.sponsorship.id = ?1")
	Double totalAmountInvoiceBySponsorship(int id);
}
