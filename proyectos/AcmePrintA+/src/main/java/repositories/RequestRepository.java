package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>{

	@Query("select r from Request r where r.order.id = ?1")
	Request findByOrderId(int orderId);
	
	@Query("select max(q.number) from PrintSpooler e join e.requests q where e.id = ?1")
	Integer findLastNumberOfPrintSpooler(int printerId);


}
