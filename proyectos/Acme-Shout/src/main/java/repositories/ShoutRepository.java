
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Shout;

@Repository
public interface ShoutRepository extends JpaRepository<Shout, Integer> {

	@Query("select count(s) from Shout s")
	public int countAllShouts();

	@Query("select count(s) from Shout s where length(s.text) <= 25")
	public int countShortShouts();

	@Query("select count(s) from Shout s where length(s.text) > 25")
	public int countLongShouts();

}
