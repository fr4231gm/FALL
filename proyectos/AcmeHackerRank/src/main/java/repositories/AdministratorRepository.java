package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Position;

@Repository
public interface AdministratorRepository extends
		JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findAdministratorByUserAccountId(int userAccountId);

	@Query("select a from Administrator a where a.userAccount.username = ?1")
	Administrator findAdministratorByUsername(String username);

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
	// the number of applications per hacker.
	@Query("select "
			+ "avg(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)), "
			+ "min(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)), "
			+ "max(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)), "
			+ "stddev(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)) "
			+ "from Hacker h")
	public Collection<Double> findAvgMinMaxStddevApplicationsPerHacker();

	// C3: The companies that have offered more positions.
	@Query("select p.company from Position p where p.isCancelled = false and p.isDraft = false group by p.company")
	public List<Company> findTop5PublishableCompanies();

	// C4: The hackers who have made more applications.
	@Query("select a.hacker from Application a where a.status is not 'PENDING' and a.submittedMoment is not null group by a.hacker")
	public List<Hacker> findTop5ApplyHackers();

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
	// the number of curricula per hacker.
	@Query("select "
			+ "min(1.0*(select count(c.hacker) from Curricula c where c.hacker.id=h.id)), "
			+ "max(1.0*(select count(c.hacker) from Curricula c where c.hacker.id=h.id)), "
			+ "avg(1.0*(select count(c.hacker) from Curricula c where c.hacker.id=h.id)), "
			+ "stddev(1.0*(select count(c.hacker) from Curricula c where c.hacker.id=h.id)) "
			+ "from Hacker h")
	public Collection<Double> findMinMaxAvgStddevCurriculaPerHacker();

	// B2: The minimum, the maximum, the average, and the standard deviation of
	// the number of results in the finders.
	@Query(" select " + "min (1.0*f.positions.size), "
			+ "max (1.0*f.positions.size), " + "avg(1.0*f.positions.size), "
			+ "stddev(1.0*f.positions.size) " + "from Finder f")
	Collection<Double> findMinMaxAvgStdevFindersResults();

	// B3: The ratio of empty versus non-empty finders.
	@Query("select 1.* (select count(fi) from Finder fi where fi.positions is empty)/count(f) from Finder f where f.positions is not empty")
	Double findRatioEmptyVsNotEmptyFinders();
	
		
}
