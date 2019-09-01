
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//incluir: p.isDraft = false and p.isCancelled = false
	@Query("select p from Position p where (p.isDraft = false and p.isCancelled = false) and (p.ticker like %?1% or p.title like %?1% or p.description like %?1% or p.skills like %?1% or p.technologies like %?1% or p.profile like %?1%) and p.deadline <=?2 and p.salary>=?3 and p.salary<=?4")
	Collection<Position> filter(String keyWord, Date deadline, Double minimumSalary, Double maximumSalary);

}
