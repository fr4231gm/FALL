
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	// Find Orders by Customer
	@Query("select o from Order o where o.customer.id = ?1")
	Collection<Order> findOrdersByCustomerId(int customerId);

	// Find Orders in final mode and customerId
	@Query("select o from Order o where o.isDraft = false and o.customer.id = ?1")
	Collection<Order> findOrdersFinalModeByCustomerId(int customerId);

	// Find Orders in final mode
	@Query("select o from Order o where o.isDraft = false")
	Collection<Order> findOrdersFinalMode();

	// Find Orders not Cancelled and customerId
	@Query("select o from Order o where o.isCancelled = false and o.customer.id = ?1")
	Collection<Order> findOrdersNotCancelled(int customerId);

	// Find Orders not Cancelled
	@Query("select o from Order o where o.isCancelled = false")
	Collection<Order> findOrdersNotCancelled();

	// Find Orders not Cancelled and in final mode
	@Query("select o from Order o where o.isCancelled = false and o.isDraft = false")
	Collection<Order> findOrdersNotCancelledFinalMode();

	// Find Orders with Applications not ACCEPTED
	@Query("select o from Order o where o.id NOT in (select a.order.id from Application a where a.status like 'ACCEPTED') and o.isDraft = false")
	Collection<Order> findOrdersWithApplicationsNotAccepted();

	@Query("select a from Application a where a.order.id = ?1 and a.status like 'ACCEPTED'")
	Application findAcceptedApplicationByOrderId(int orderId);

	@Query("select o from Order o where o.id NOT in (select a.order.id from Application a where a.status like 'ACCEPTED') and o.isDraft = false and o.customer.id = ?1")
	Collection<Order> findOrdersByCustomerIdWithNoPaymant(int id);

}
