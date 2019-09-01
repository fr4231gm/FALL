
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.order.customer.id=?1")
	Collection<Application> findApplicationsByCustomerId(int customerId);

	@Query("select a from Application a where a.order.id=?1")
	Collection<Application> findApplicationsByOrderId(int orderId);

	@Query("select a from Application a where a.company.id=?1 and a.order.isCancelled = 'false'")
	Collection<Application> findApplicationsByCompanyId(int companyId);

	@Query("select a from Application a where a.order.id=?1 and a.order.customer.id=?2 and a.status='PENDING'")
	Collection<Application> findApplicationsPendingsByOrderAndCustomer(int orderId, int customerId);

	@Query("select a from Application a where a.order.id=?1 and a.order.customer.id=?2")
	Collection<Application> findApplicationsByOrderAndCustomer(int orderId, int customerId);

	@Query("select a from Application a where a.order.id = ?1 and a.status='ACCEPTED'")
	Application findAcceptedApplicationByOrderId(int orderId);
}
