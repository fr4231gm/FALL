
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	// Find Position by its Ticker
	@Query("select p from Position p where p.ticker = ?1")
	Position findPositionByTicker(String ticker);

	//Search position anonymous
	@Query("select p from Position p where (p.isCancelled = false and p.isDraft = false) and (p.title like %?1% or p.description like %?1% or p.skills like %?1% or p.technologies like %?1% or p.profile like %?1% or p.company.name like %?1%)")
	Collection<Position> filter(String keyWord);

	// Find Positions by Company
	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> findPositionsByCompanyId(int companyId);

	// Find Positions in final mode
	@Query("select p from Position p where p.isDraft = false and p.company.id = ?1")
	Collection<Position> findPositionsFinalModeByCompany(int companyId);

	// Find Positions in final mode
	@Query("select p from Position p where p.isDraft = false")
	Collection<Position> findPositionsFinalMode();

	// Find Positions not Cancelled
	@Query("select p from Position p where p.isCancelled = false and p.company.id = ?1")
	Collection<Position> findPositionsNotCancelled(int companyId);

	// Find Positions not Cancelled
	@Query("select p from Position p where p.isCancelled = false")
	Collection<Position> findPositionsNotCancelled();

	// Find Positions not Cancelled and in final mode
	@Query("select p from Position p where p.isCancelled = false and p.isDraft = false")
	Collection<Position> findPositionsNotCancelledFinalMode();

	@Query("select p from Position p where (p.isDraft = false and p.isCancelled = false) and (p.ticker like %?1% or p.title like %?1% or p.description like %?1% or p.skills like %?1% or p.technologies like %?1% or p.profile like %?1%) and p.deadline <=?2 and p.salary>=?3 and p.salary<=?4")
	Collection<Position> filter(String keyWord, Date deadline, Double minimumSalary, Double maximumSalary);

}
