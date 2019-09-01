package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select h from History h where h.brotherhood.id = ?1")
	History findByBrotherhoodId(int brotherhoodId);

	@Query("select h from History h where h.inceptionRecord.id = ?1")
	History findByInceptionId(int id);

}
