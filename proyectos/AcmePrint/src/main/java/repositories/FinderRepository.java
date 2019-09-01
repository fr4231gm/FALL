
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Order;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select o from Order o where o.isDraft = false and o.isCancelled = false and o.id NOT in (select a.order.id from Application a where a.status like 'ACCEPTED') and (o.comments like %?1% or o.material like %?1% or o.status like %?1% and o.moment <=?2 and o.moment>=?3)")
	Collection<Order> filter(String keyWord, Date minDate, Date maxDate);

	@Query("select f from Finder f where f.keyword = ?1 and f.minDate = ?2 and f.maxDate = ?3")
	Collection<Finder> filterFinder(String keyWord, Date minDate, Date maxDate);

	//Find Finder By OrderId
	@Query("select f from Finder f join f.orders o where o.id= ?1")
	Finder findByOrderId(final int orderId);

}
