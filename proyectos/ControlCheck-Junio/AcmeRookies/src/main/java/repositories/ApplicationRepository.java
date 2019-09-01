
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.rookie.id=?1")
	Collection<Application> findApplicationsByRookie(int rookieId);

	@Query("select a from Application a where a.position.id=?1")
	Collection<Application> findApplicationsByPosition(int positionId);

	@Query("select a from Application a join a.position p where p.company.id=?1")
	Collection<Application> findApplicationsByCompany(int companyId);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPosition(int positionId);
}
