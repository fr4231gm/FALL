package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	// Find Rookie by the userAccountId
	@Query("select b from Rookie b where b.userAccount.id=?1")
	Rookie findRookieByUserAccount(int userAccountId);

}
