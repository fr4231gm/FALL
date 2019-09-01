package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	// Find Hacker by the userAccountId
	@Query("select b from Hacker b where b.userAccount.id=?1")
	Hacker findHackerByUserAccount(int userAccountId);

}
