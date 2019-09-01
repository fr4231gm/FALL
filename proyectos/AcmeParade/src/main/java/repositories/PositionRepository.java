
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	// Find position by name
	@Query("select p from Position p where KEY(p.name)=?1")
	Position findPositionByLanguage(String language);

}
