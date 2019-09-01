
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import domain.Position;
import domain.Procession;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//C1: Return the average, the minimum, the maximum, and the standard deviation of the number of members per brotherhood
	@Query("select avg(1.0*(select count(e.brotherhood) from Enrolment e where e.brotherhood.id=b.id)), " + "min(1*(select count(e.brotherhood) from Enrolment e where e.brotherhood.id=b.id)), "
		+ "max(1*(select count(e.brotherhood) from Enrolment e where e.brotherhood.id=b.id)), " + "stddev(1.0*(select count(e.brotherhood) from Enrolment e where e.brotherhood.id=b.id)) " + "from Brotherhood b")
	Collection<Double> findMemberPerBrotherhoodStats();

	//C2: Return the largest brotherhoods. Limit it to 3 at service.
	//select e.brotherhood, count(e) as c from Enrolment e group by e.brotherhood order by c DESC;
	@Query("select e.brotherhood from Enrolment e where e.dropOutMoment is null group by e.brotherhood order by count(e.brotherhood) desc")
	List<Brotherhood> findLargestBrotherhoods();
	
	//C3: Return the smallest brotherhood. Limit it to 3 at service.
	@Query("select e.brotherhood from Enrolment e where e.dropOutMoment is null group by e.brotherhood order by count(e.brotherhood) asc")
	List<Brotherhood> findSmallestBrotherhoods();
	
	//C4-1: Return the ratio of requests to march in a procession, grouped by their status.APPROVED
	@Query("select 1.* (select count(r) from Request r where r.procession is not null and r.status='APPROVED')/count(rs) from Request rs")
	Double findRatioRequestsByStatusApproved();
	
	//C4-2: Return the ratio of requests to march in a procession, grouped by their status.REJECTED
	@Query("select 1.* (select count(r) from Request r where r.procession is not null and r.status='REJECTED')/count(rs) from Request rs")
	Double findRatioRequestsByStatusRejected();
	
	//C4-3: Return the ratio of requests to march in a procession, grouped by their status.PENDING
	@Query("select 1.* (select count(r) from Request r where r.procession is not null and r.status='PENDING')/count(rs) from Request rs")
	Double findRatioRequestsByStatusPending();
	
	//C4-4: The ratio of requests to march grouped by status.APPROVED
	@Query("select 1.* (select count(r) from Request r where r.procession.id=?1 and r.status='APPROVED')/count(rs) from Request rs where rs.procession.id=?1")
	Double findRatioRequestsByStatusApproved(final int processionId);
	
	//C4-5: The ratio of requests to march grouped by status.REJECTED
	@Query("select 1.* (select count(r) from Request r where r.procession.id=?1 and r.status='REJECTED')/count(rs) from Request rs where rs.procession.id=?1")
	Double findRatioRequestsByStatusRejected(final int processionId);
	
	//C4-6: The ratio of requests to march grouped by status.PENDING
	@Query("select 1.* (select count(r) from Request r where r.procession.id=?1 and r.status='PENDING')/count(rs) from Request rs where rs.procession.id=?1")
	Double findRatioRequestsByStatusPending(final int processionId);
	
	//C5: Return the processions that are going to be organised in 30 days or less.
	@Query("select p from Procession p where p.moment between ?1 and ?2")
	Collection<Procession> findFollowingProcessions(Date start, Date end);
	
	//C6: The listing of members who have got at least 10% the maximum number of request to march accepted.
	@Query("select r.member from Request r where r.status = 'APPROVED' group by r.member having count(r) > (select 0.1*count(re) from Request re where re.status='APPROVED')")
	Collection<Member> findMembersApproved();

	//C7: A histogram of positions.
	//@Query("select e.position from Enrolment e group by e.position")
	@Query("select e.position, count(e) from Enrolment e group by e.position")
	Collection<Position> findPositionsHistogram();
	
	
	//B1-1: The ratio of the number of brotherhoods per area.
	@Query("select 1.0*count(b)/count(distinct a) from Area a, Brotherhood b where a.id = b.area.id")
	Double findRatioBrotherhoodsPerArea();
	
	//B1-2: The count of the number of brotherhoods per area.
	@Query("select a.id, 1*(select count(b.area) from Brotherhood b where b.area.id = a.id) from Area a")
	Collection<Integer> findCountBrotherhoodsPerArea();
	
	//B1-3: The min of the number of brotherhoods per area.
	@Query("select min(1.0*(select count(b.area) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double findMinBrotherhoodsPerArea();	
	
	//B1-4: The max of the number of brotherhoods per area.
	@Query("select max(1.0*(select count(b.area) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double findMaxBrotherhoodsPerArea();
	
	//B1-5: The avg of the number of brotherhoods per area.
	@Query("select avg(1.0*(select count(b.area) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double findAvgBrotherhoodsPerArea();
	
	//B1-6: The standard deviation of the number of brotherhoods per area.
	@Query("select stddev(1.0*(select count(b.area) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double findStddevBrotherhoodsPerArea();
	
	//B2: The minimum, the maximum, the average, and the standard deviation of 
	//the number of results in the finders.
	@Query(" select " +
			"min (1.0*f.processions.size), " +
			"max (1.0*f.processions.size), " +
			"avg(1.0*f.processions.size), " + 
			"sqrt( 1.0*(sum(f.processions.size*f.processions.size)/count(f.processions.size)-(avg(f.processions.size)*avg(f.processions.size))))" +
			"from Finder f")
	Collection<Double> findMinMaxAvgStdevFindersResults();
	
	//B3:  The ratio of empty versus non-empty finders.
	@Query("select 1.* (select count(fi) from Finder fi where fi.processions is empty)/count(f) from Finder f where f.processions is not empty")
	Double findRatioEmptyVsNotEmptyFinders();
	
	//A+1:  The ratio of spammers and not spammers.
	@Query("select avg(a.spammer) from Actor a")
	Double findSpammersRatio();
	
	//A+2:  The average polarity.
	@Query("select avg(a.polarity) from Actor a")
	Double findPolarityAverage();
	
	@Query("select a from Administrator a where a.userAccount.id=?1")
	Administrator findAdministratorByUserAccount(int userAccountId);
}
