
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	// Find a Parade by its Ticker
	@Query("select p from Parade p where p.ticker = ?1")
	Parade findParadeByTicker(String ticker);

	//Find all Parade by BrotherhoodId
	@Query("select p from Parade p where p.brotherhood.id = ?1")
	Collection<Parade> findParadesByBrotherhoodId(int brotherhoodId);

	// Find Parades belonging to Brotherhood which a member belongs to
	@Query("select p from Parade p where p.brotherhood.id in (select distinct e.brotherhood.id from Enrolment e where e.member.id = ?1)")
	Collection<Parade> findParadesByMemberId(int memberId);

	//Find not Draft Parades 
	@Query("select p from Parade p where p.status='ACCEPTED' and p.isDraft = false and p.brotherhood.id = ?1")
	Collection<Parade> findParadesNoDraftByBrotherhood(int brotherhoodId);

	// Find requestabes Parades by member
	@Query("select p from Parade p where p.brotherhood.id in (select distinct e.brotherhood.id from Enrolment e where e.member.id = ?1) and p.status = 'ACCEPTED' and p.isDraft = false and p.id not in (select distinct r.parade.id from Request r where r.member.id = ?1)")
	Collection<Parade> findParadesRequestablesByMemberId(int memberId);

	@Query("select p from Parade p where p.brotherhood.area.id = ?1")
	Collection<Parade> findByArea(int id);

	//Find parades with Status ACCEPTED
	@Query("select p from Parade p where p.status = 'ACCEPTED' and p.isDraft = false and p.brotherhood.id = ?1")
	Collection<Parade> findParadesWithStatusAccepted(final int brotherhoodId);

	// Find parades with draf=false and status=accepted
	@Query("select p from Parade p where p.status = 'ACCEPTED' and p.isDraft = false")
	Collection<Parade> findParadesAccepted();
}
