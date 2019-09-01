
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Procession p where (p.ticker like %?1% or p.title like %?1% or p.description like %?1%) and p.brotherhood.area.name like %?2% and p.moment>=?3 and p.moment<=?4")
	Collection<Procession> filter(String keyWord, String area, Date startDate, Date endDate);

}
