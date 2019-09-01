
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//FindAdministratorByID
	@Query("select a from Administrator a where a.id = ?1")
	Administrator findById(String id);

	//Query C/1The average, the minimum, the maximum, and the standard deviation of the number of fix-up tasks per user.
	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	Collection<Object[]> findFixUpTaskPerUserStats();

	//Query C/2 The average, the minimum, the maximum, and the standard deviation of the number of applications per fix-up task. 
	@Query("select avg(f.applications.size), min(f.applications.size), max(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	Collection<Object[]> findApplicationPerFixUpTaskStats();

	//Query C/3 The average, the minimum, the maximum, and the standard deviation of the maximum price of the fix-up tasks
	@Query("select avg(f.maximumPrice), min(f.maximumPrice), max(f.maximumPrice), stddev(f.maximumPrice) from FixUpTask f")
	Collection<Object[]> findMaxPricePerFixUpTaskStats();

	//Query C/4 The average, the minimum, the maximum, and the standard deviation of the price offered in the applications.
	@Query("select avg(a.price), min(a.price), max(a.price), stddev(a.price) from Application a")
	Collection<Object[]> findPricePerApplicationStats();

	//Query C/5 The ratio of pending applications
	@Query("select 1.0*(select count(a1) from Application a1 where a1.status = 'PENDING')/count(a) from Application a")
	Double findPendingApplications();

	//Query C/6 The ratio of accepted applications
	@Query("select 1.0*(select count(a1) from Application a1 where a1.status = 'ACCEPTED')/count(a) from Application a)")
	Double findRatioOfAcceptedApplications();

	//Query C/7 The ratio of rejected applications
	@Query("select 1.0*(select count(a1) from Application a1 where a1.status = 'REJECTED')/count(a) from Application a)")
	Double findRatioOfRejectedApplications();

	//Query C/8 The ratio of pending applications that cannot change its status because their time period is elapsed
	@Query("select 1.0*(select count(a1) from Application a1 where a1.status = 'PENDING' and a1.fixUpTask.endDate < CURRENT_DATE)/count(a) from Application a")
	Double findRatioOfPendingApplications();

	//Query C/9 The listing of customers who have published at least 10% more fix-up tasks than the average, ordered by number of applications. 
	@Query("select c from Customer c where c.fixUpTasks.size > (select avg(c.fixUpTasks.size)*1.1 from Customer c) order by c.fixUpTasks.size")
	Collection<Customer> findCustomerFixUpTasksAboveAverage();

	//Query C/10 The listing of handy workers who have got accepted at least 10% more applications than the average, ordered by number of applications.
	@Query("select h from HandyWorker h join h.applications as a where h.applications.size > (select avg(h.applications.size)*1.1 from HandyWorker h) and a.status='ACCEPTED' order by h.applications.size")
	Collection<HandyWorker> findHandyWorkersApplicationAboveAverage();

	//Query B/1 The minimum, the maximum, the average, and the standard deviation of the number of complaints per fix-up task. 
	@Query("select min(f.complaints.size), max(f.complaints.size), avg(f.complaints.size), stddev(f.complaints.size) from FixUpTask f join f.complaints as c where c.isDraft = false")
	Collection<Double> findComplaintStats();

	//Query B/2 The minimum, the maximum, the average, and the standard deviation of the number of notes per referee report.
	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), stddev(r.notes.size) from Report r")
	Collection<Double> findNumberOfNotesPerRefereeStats();

	//Query B/3 The ratio of fix-up tasks with a complaint.
	@Query("select 1.0*(select count(f2) from FixUpTask f2 where f2.complaints IS NOT EMPTY)/count(f1) from FixUpTask f1")
	Double findRatioOfFixUpTaskWithComplaint();

	//Query B/4 The top-three customers in terms of complaints.	
	@Query("select distinct(c) from Customer c join c.fixUpTasks as f join f.complaints as cs where cs.isDraft=false order by cs.size DESC")
	Collection<Customer> findTopThreeCustomerByComplaints();

	//Query B/5 The top-three handy workers in terms of complaints.
	@Query("select distinct(h) from HandyWorker h join h.applications as a join a.fixUpTask.complaints as c where c.isDraft=false order by c.size DESC")
	Collection<HandyWorker> findTopThreeHandyWorkerByComplaints();
}
