
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select h from Curricula h where h.hacker.id = ?1")
	Collection<Curricula> findByHackerId(int hackerId);

	@Query("select h from Curricula h where h.personalData.id = ?1")
	Curricula findByPersonalDataId(int id);

	@Query("select a from Curricula a join a.miscellaneousData p where p.id= ?1")
	Curricula findByMiscellaneousDataId(int id);

	@Query("select a from Curricula a join a.educationData p where p.id= ?1")
	Curricula findByEducationDataId(int id);

	@Query("select h from Curricula h where h.hacker.id = ?1 and h not in (select p.curricula from Application p)")
	Collection<Curricula> findNonCopiesByPrincipal(int id);

	@Query("select a from Curricula a join a.positionData p where p.id= ?1")
	Curricula findByPositionDataId(int id);

	@Query("select h from Curricula h where h.id = ?1 and h in (select p.curricula from Application p)")
	Curricula findCopiedById(int id);
}
