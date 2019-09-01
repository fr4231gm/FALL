
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.Parade;
import domain.Position;
import domain.Sponsor;

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
	
	//C4-1: Return the ratio of requests to march in a parade, grouped by their status.APPROVED
	@Query("select 1.* (select count(r) from Request r where r.parade is not null and r.status='APPROVED')/count(rs) from Request rs")
	Double findRatioRequestsByStatusApproved();
	
	//C4-2: Return the ratio of requests to march in a parade, grouped by their status.REJECTED
	@Query("select 1.* (select count(r) from Request r where r.parade is not null and r.status='REJECTED')/count(rs) from Request rs")
	Double findRatioRequestsByStatusRejected();
	
	//C4-3: Return the ratio of requests to march in a parade, grouped by their status.PENDING
	@Query("select 1.* (select count(r) from Request r where r.parade is not null and r.status='PENDING')/count(rs) from Request rs")
	Double findRatioRequestsByStatusPending();
	
	//C4-4: The ratio of requests to march grouped by status.APPROVED
	@Query("select 1.* (select count(r) from Request r where r.parade.id=?1 and r.status='APPROVED')/count(rs) from Request rs where rs.parade.id=?1")
	Double findRatioRequestsByStatusApproved(final int paradeId);
	
	//C4-5: The ratio of requests to march grouped by status.REJECTED
	@Query("select 1.* (select count(r) from Request r where r.parade.id=?1 and r.status='REJECTED')/count(rs) from Request rs where rs.parade.id=?1")
	Double findRatioRequestsByStatusRejected(final int paradeId);
	
	//C4-6: The ratio of requests to march grouped by status.PENDING
	@Query("select 1.* (select count(r) from Request r where r.parade.id=?1 and r.status='PENDING')/count(rs) from Request rs where rs.parade.id=?1")
	Double findRatioRequestsByStatusPending(final int paradeId);
	
	//C5: Return the parades that are going to be organised in 30 days or less.
	@Query("select p from Parade p where p.moment between ?1 and ?2")
	Collection<Parade> findFollowingParades(Date start, Date end);
	
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
			"min (1.0*f.parades.size), " +
			"max (1.0*f.parades.size), " +
			"avg(1.0*f.parades.size), " + 
			"sqrt( 1.0*(sum(f.parades.size*f.parades.size)/count(f.parades.size)-(avg(f.parades.size)*avg(f.parades.size))))" +
			"from Finder f")
	Collection<Double> findMinMaxAvgStdevFindersResults();
	
	//B3:  The ratio of empty versus non-empty finders.
	@Query("select 1.* (select count(fi) from Finder fi where fi.parades is empty)/count(f) from Finder f where f.parades is not empty")
	Double findRatioEmptyVsNotEmptyFinders();
	
	//A+1:  The ratio of spammers and not spammers.
	@Query("select avg(a.spammer) from Actor a")
	Double findSpammersRatio();
	
	//A+2:  The average polarity.
	@Query("select avg(a.polarity) from Actor a")
	Double findPolarityAverage();
	
	@Query("select a from Administrator a where a.userAccount.id=?1")
	Administrator findAdministratorByUserAccount(int userAccountId);
	
	/*****************************ACME-PARADE*********************************/
	//C1:The average, the minimum, the maximum, and the standard deviation 
	//of the number of records per history.
	@Query("select " 
	+ "avg(1+h.miscellaneousRecord.size+h.legalRecord.size+h.periodRecord.size+h.linkRecord.size), " 
	+ "min(1+h.miscellaneousRecord.size+h.legalRecord.size+h.periodRecord.size+h.linkRecord.size), "
	+ "max(1+h.miscellaneousRecord.size+h.legalRecord.size+h.periodRecord.size+h.linkRecord.size), " 
	+ "stddev(1+h.miscellaneousRecord.size+h.legalRecord.size+h.periodRecord.size+h.linkRecord.size) " 
	+ "from History h")
	Collection<Double> findAvgMinMaxStdevRecordsPerHistory();
	
	//C2: The brotherhood with the largest history.
	//We consider the brotherhood whose history has the max number of records.
	@Query("select h.brotherhood from History h group by h.brotherhood having max(1+h.miscellaneousRecord.size+h.legalRecord.size+h.periodRecord.size+h.linkRecord.size)>1")
	List<Brotherhood> findLargestBrotherhood();
	
	//C3: The brotherhoods whose history is larger than the average
	@Query("select h.brotherhood from History h group by h.brotherhood  having avg(1+h.miscellaneousRecord.size+h.legalRecord.size+h.periodRecord.size+h.linkRecord.size)> (select avg(1+hi.miscellaneousRecord.size+hi.legalRecord.size+hi.periodRecord.size+hi.linkRecord.size) from History hi)")
	List<Brotherhood> findBrotherhoodsLargestHistory();
	
	//B1: The ratio of areas that are not co-ordinated by any chapters.
	@Query("select 1.* (select count(a) from Area a where a.chapter is empty)/count(ar) from Area ar")
	Double findRatioAreasNotCoordinated();
	
	//B2: The average, the minimum, the maximum, and the standard deviation of the
	//number of parades co-ordinated by the chapters
	@Query("select "
			+ "avg(1.0*(select count(p) from Parade p where p.brotherhood.area.chapter.id=c.id and p.isDraft = false)),"
			+ "min(1.0*(select count(p) from Parade p where p.brotherhood.area.chapter.id=c.id and p.isDraft = false)),"
			+ "max(1.0*(select count(p) from Parade p where p.brotherhood.area.chapter.id=c.id and p.isDraft = false)),"
			+ "stddev(1.0*(select count(p) from Parade p where p.brotherhood.area.chapter.id=c.id and p.isDraft = false))"
			+ " from Chapter c")
	Collection<Double> findAvgMinMaxStdevParadesPerChapters();
	
	//B3: The chapters that co-ordinate at least 10% more parades than the average.
	@Query("select p.brotherhood.area.chapter from Parade p where p.isDraft = false group by p.brotherhood.area.chapter having count (p)> 1.0*(select 1.1*avg(1.0*(select count(p) from Parade p where p.brotherhood.area.chapter.id=c.id and p.isDraft = false)) from Chapter c)")
	Collection<Chapter> findActiveChapters();
	
	//B4: The ratio of parades in draft mode versus parades in final mode.
	@Query("select 1.* (select count(pa) from Parade pa where pa.isDraft = true)/count(p) from Parade p where p.isDraft = false")
	Double findRatioDraftVsFinalModeParades();
	
	//B5: The ratio of parades in final mode grouped by status.
	//select p.status, 1.* (select count(pa) from Parade pa where pa.isDraft = false)/count(p) from Parade p where p.isDraft = false group by p.status
	@Query("select " 
	+ "1.* (select count(pa) from Parade pa where pa.isDraft = false and pa.status = 'SUBMITTED')/count(p),"
	+ "1.* (select count(pa) from Parade pa where pa.isDraft = false and pa.status = 'ACCEPTED')/count(p),"
	+ "1.* (select count(pa) from Parade pa where pa.isDraft = false and pa.status = 'REJECTED')/count(p)"
	+ " from Parade p where p.isDraft = false and p.status is not empty")
	Collection<Double> findRatioParadesByStatus();
	
	//A1: The ratio of active sponsorships.
	@Query("select 1.* (select count(s) from Sponsorship s where s.isEnabled = true )/count(sp) from Sponsorship sp")
	Double findRatioActiveSponsorships();
	
	//A2: The average, the minimum, the maximum, and the standard deviation of active sponsorships per sponsor.
	@Query("select "
			+ "avg(1.0*(select count(sp) from Sponsorship sp where sp.sponsor.id=s.id and sp.isEnabled = true)),"
			+ "min(1.0*(select count(sp) from Sponsorship sp where sp.sponsor.id=s.id and sp.isEnabled = true)),"
			+ "max(1.0*(select count(sp) from Sponsorship sp where sp.sponsor.id=s.id and sp.isEnabled = true)),"
			+ "stddev(1.0*(select count(sp) from Sponsorship sp where sp.sponsor.id=s.id and sp.isEnabled = true))"
			+ " from Sponsor s")
	Collection<Double> findAvgMinMaxStdevSponsorshipsPerSponsor();
	
	//A3: The top-5 sponsors in terms of number of active sponsorships.
	//select sp.sponsor, count(sp) from Sponsorship sp where sp.isEnabled = true group by sp.sponsor
	@Query("select sp.sponsor from Sponsorship sp where sp.isEnabled = true group by sp.sponsor")
	List<Sponsor> findTop5Sponsors();
}
