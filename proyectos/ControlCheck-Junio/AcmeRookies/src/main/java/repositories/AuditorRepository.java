package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Auditor;

public interface AuditorRepository extends JpaRepository<Auditor,Integer> {

	//Find Auditor by the userAccountId
	@Query("select a from Auditor a where a.userAccount.id=?1")
	Auditor findAuditorByUserAccount(int userAccountId);

	@Query("select a from Auditor a join a.positions p where p.id=?1")
	Collection<Auditor> findByPosition(int positionId);
}
