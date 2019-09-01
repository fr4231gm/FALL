
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

	@Query("select i from Invoice i where i.application.id = ?1")
	Invoice findInvoiceByApplicationId(int applicationId);

	@Query("select i from Invoice i where i.application.company.id = ?1")
	Collection<Invoice> findInvoicesByCompanyId(int companyId);

	@Query("select i from Invoice i where i.application.order.id = ?1")
	Invoice findInvoiceByOrderId(int orderId);

	@Query("select i from Invoice i where i.application.order.customer.id =?1")
	Collection<Invoice> findInvoicesByCustomerId(int customerId);
}
