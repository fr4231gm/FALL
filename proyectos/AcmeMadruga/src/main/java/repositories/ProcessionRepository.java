package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends
		JpaRepository<Procession, Integer> {

	// Find a Procession by its Ticker
	@Query("select p from Procession p where p.ticker = ?1")
	Procession findProcessionByTicker(String ticker);
	
	//Find all Procession by BrotherhoodId
	@Query("select p from Procession p where p.brotherhood.id = ?1")
	Collection<Procession> findProcessionsByBrotherhoodId(int brotherhoodId);

	// Find Processions belonging to Brotherhood which a member belongs to
	@Query("select p from Procession p where p.brotherhood.id in (select distinct e.brotherhood.id from Enrolment e where e.member.id = ?1)")
	Collection<Procession> findProcessionsByMemberId(int memberId);

	//Find not Draft Processions 
	@Query("select p from Procession p where p.isDraft = false and p.brotherhood.id = ?1")
	Collection<Procession> findProcessionsNoDraftByBrotherhood(int brotherhoodId);
	
	// Find requestabes Processions by member
	@Query("select p from Procession p where p.brotherhood.id in (select distinct e.brotherhood.id from Enrolment e where e.member.id = ?1) and p.isDraft = false and p.id not in (select distinct r.procession.id from Request r where r.member.id = ?1)")
	Collection<Procession> findProcessionsRequestablesByMemberId(int memberId);
}
