
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	//FindComplaintsWithoutReferee
	@Query("select c from Complaint c where c.referee.id = null")
	Collection<Complaint> findWithoutReferee();

	//FindSelfAssignedComplaints
	@Query("select c from Complaint c where c.referee.id = ?1")
	Collection<Complaint> findSelfAssignedComplaints(int id);

	//FindComplaintsWhereTheHandyWorkerIsInvolved preguntar por query 
	@Query("select c from Complaint c join c.fixUpTask.applications as a where a.handyWorker.id=?1")
	Collection<Complaint> findInvolvedComplaint(Integer id);

	//FindCOmplaintDeCustomer
	@Query("select c from Complaint c where c.customer.id = ?1")
	Collection<Complaint> findCustomerComplaints(int id);

	//FindCOmplaintDeReferee
	@Query("select c from Complaint c where c.referee.id = ?1")
	Collection<Complaint> findAssignedComplaints(int id);

}
