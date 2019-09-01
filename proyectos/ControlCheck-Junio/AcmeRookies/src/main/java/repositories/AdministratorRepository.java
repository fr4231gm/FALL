package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;

@Repository
public interface AdministratorRepository extends
		JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findAdministratorByUserAccountId(int userAccountId);

	@Query("select a from Administrator a where a.userAccount.username = ?1")
	Administrator findAdministratorByUsername(String username);

	/***************************ACME-HACKER-RANK******************************/
	
	// C-level dashboard ------------------------------------------------------

	// C1: The average, the minimum, the maximum, and the standard deviation of
	// the number of positions per company.
	@Query("select "
			+ "avg(1.0*(select count(p.company) from Position p where p.company.id=c.id)), "
			+ "min(1.0*(select count(p.company) from Position p where p.company.id=c.id)), "
			+ "max(1.0*(select count(p.company) from Position p where p.company.id=c.id)), "
			+ "stddev(1.0*(select count(p.company) from Position p where p.company.id=c.id)) "
			+ "from Company c")
	public Collection<Double> findAvgMinMaxStddevPositionsPerCompany();

	// C2: The average, the minimum, the maximum, and the standard deviation of
	// the number of applications per rookie.
	@Query("select "
			+ "avg(1.0*(select count(a.rookie) from Application a where a.rookie.id=h.id)), "
			+ "min(1.0*(select count(a.rookie) from Application a where a.rookie.id=h.id)), "
			+ "max(1.0*(select count(a.rookie) from Application a where a.rookie.id=h.id)), "
			+ "stddev(1.0*(select count(a.rookie) from Application a where a.rookie.id=h.id)) "
			+ "from Rookie h")
	public Collection<Double> findAvgMinMaxStddevApplicationsPerRookie();

	// C3: The companies that have offered more positions.
	@Query("select p.company from Position p where p.isCancelled = false and p.isDraft = false group by p.company")
	public List<Company> findTop5PublishableCompanies();

	// C4: The rookies who have made more applications.
	@Query("select a.rookie from Application a where a.status is not 'PENDING' and a.submittedMoment is not null group by a.rookie")
	public List<Rookie> findTop5ApplyRookies();

	// C5: The average, the minimum, the maximum, and the standard deviation of
	// the salaries offered.
	@Query("select "
			+ "avg(p.salary), "
			+ "min(p.salary), "
			+ "max(p.salary), "
			+ "stddev(p.salary) "
			+ "from Position p where p.isDraft = false and p.isCancelled = false")
	public Collection<Double> findAvgMinMaxStddevSalaryOffered();

	// C6-1: The best and the worst position in terms of salary.
	// BEST
	@Query("select po from Position po where po.salary in (select max(p.salary) from Position p where p.isDraft = false and p.isCancelled = false)")
	public List<Position> findBestPositionBySalary();

	// C6-2: The best and the worst position in terms of salary.
	// WORST
	@Query("select po from Position po where po.salary in (select min(p.salary) from Position p where p.isDraft = false and p.isCancelled = false)")
	public List<Position> findWorstPositionBySalary();

	// B-level dashboard ------------------------------------------------------

	// B1: The minimum, the maximum, the average, and the standard deviation of
	// the number of curricula per rookie.
	@Query("select "
			+ "min(1.0*(select count(c.rookie) from Curricula c where c.rookie.id=h.id)), "
			+ "max(1.0*(select count(c.rookie) from Curricula c where c.rookie.id=h.id)), "
			+ "avg(1.0*(select count(c.rookie) from Curricula c where c.rookie.id=h.id)), "
			+ "stddev(1.0*(select count(c.rookie) from Curricula c where c.rookie.id=h.id)) "
			+ "from Rookie h")
	public Collection<Double> findMinMaxAvgStddevCurriculaPerRookie();

	// B2: The minimum, the maximum, the average, and the standard deviation of
	// the number of results in the finders.
	@Query(" select " + "min (1.0*f.positions.size), "
			+ "max (1.0*f.positions.size), " + "avg(1.0*f.positions.size), "
			+ "stddev(1.0*f.positions.size) " + "from Finder f")
	Collection<Double> findMinMaxAvgStdevFindersResults();

	// B3: The ratio of empty versus non-empty finders.
	@Query("select 1.* (select count(fi) from Finder fi where fi.positions is empty)/count(f) from Finder f where f.positions is not empty")
	Double findRatioEmptyVsNotEmptyFinders();
	
	/*******************************ACME-ROOKIE*******************************/
	
	// C-level dashboard ------------------------------------------------------
	
	// C1: The average, the minimum, the maximum, and the standard deviation of the
	// audit score of the positions stored in the system.
	@Query("select  " + "avg (1.0* a.score), "
	+ "min (1.0* a.score), " + "max (1.0* a.score), "
	+ "stddev (1.0* a.score) " + "from Audit a where a.isDraft is false")
	Collection<Double> findAvgMinMaxStddevAuditsScore();
	
	// C2: The average, the minimum, the maximum, and the standard deviation of 
	// the audit score of the companies that are registered in the system.
	@Query("select  " + "avg (1.0* c.score), "
	+ "min (1.0* c.score), " + "max (1.0* c.score), "
	+ "stddev (1.0* c.score) " + "from Company c")
	Collection<Double> findAvgMinMaxStddevCompanyScore();
	
	// C3: The companies with the highest audit score
	// Note: We considered a Top3 companies with the highest audit score
	@Query("select c from Company c group by c.score")
	List<Company> findTop5Companies();
	
	// C4: The average salary offered by the positions that have the highest 
	// average audit score.
	@Query("select avg(1.0*po.salary) from Position po where po.company in (select c from Company c group by c.score)")
	List<Double> findTopSalaryPositions();
	
	// B1: The minimum, the maximum, the average, and the standard deviation of 
	// the number of items per provider.
	@Query("select " +
	"min (1.0*(select count(i) from Item i where i.provider.id = p.id)), " +
	"max (1.0*(select count(i) from Item i where i.provider.id = p.id)), " +
	"avg (1.0*(select count(i) from Item i where i.provider.id = p.id)), " +
	"stddev (1.0*(select count(i) from Item i where i.provider.id = p.id)) " +
	"from Provider p")
	Collection<Double> findMinMaxAvgStddevItemsPerProvider();
	
	// B2: The top-5 providers in terms of total number of items provided
	@Query("select i.provider from Item i group by i.provider order by count(i) desc")
	List<Provider> findTop5Providers();
	
	// A1: The average, the minimum, the maximum, and the standard deviation of 
	// the number of sponsorships per provider.
	// Note: We consider active sponsorships
	@Query("select "
			+ "avg(1.0*(select count(sp) from Sponsorship sp where sp.provider.id=p.id and sp.isEnabled = true)),"
			+ "min(1.0*(select count(sp) from Sponsorship sp where sp.provider.id=p.id and sp.isEnabled = true)),"
			+ "max(1.0*(select count(sp) from Sponsorship sp where sp.provider.id=p.id and sp.isEnabled = true)),"
			+ "stddev(1.0*(select count(sp) from Sponsorship sp where sp.provider.id=p.id and sp.isEnabled = true))"
			+ " from Provider p")
	Collection<Double> findAvgMinMaxStddevSponsorshipsPerProvider();
	
	// A2: The average, the minimum, the maximum, and the standard deviation of 
	// the number of sponsorships per position.
	// Note: We consider active sponsorships
	@Query("select "
			+ "avg(1.0*(select count(sp) from Sponsorship sp where sp.position.id=p.id and sp.isEnabled = true)),"
			+ "min(1.0*(select count(sp) from Sponsorship sp where sp.position.id=p.id and sp.isEnabled = true)),"
			+ "max(1.0*(select count(sp) from Sponsorship sp where sp.position.id=p.id and sp.isEnabled = true)),"
			+ "stddev(1.0*(select count(sp) from Sponsorship sp where sp.position.id=p.id and sp.isEnabled = true))"
			+ " from Position p")
	Collection<Double> findAvgMinMaxStddevSponsorshipsPerPosition();
	
	// A3: The providers who have a number of sponsorships that is at least 10% 
	// above the average number of sponsorships per provider.
	// Note: We consider active sponsorships
	@Query("select sp.provider from Sponsorship sp where sp.isEnabled = true group by sp.provider having count(sp) > (select avg(1.1*(select count(s) from Sponsorship s where s.provider.id=p.id and s.isEnabled = true)) from Provider p)")
	Collection<Provider> findActiveProviders();
	
	/*************************************************************************/
	
	// Find Spammer Actors to flag as spammers
	@Query("select m.sender from Message m where m.isSpam = true group by m.sender having count(m) > ( select 0.1*count(me) from Message me where me.isSpam=true )")
	public Collection<Actor> findSpammersToFlag();
}
