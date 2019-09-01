
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	// Find Brotherhood by the userAccountId
	@Query("select b from Brotherhood b where b.userAccount.id=?1")
	Brotherhood findBrotherhoodByUserAccount(int userAccountId);
	
	// Find Brotherhoods which a member doesn`t belong to
	@Query("select b from Brotherhood b where b not in (select e.brotherhood from Enrolment e where e.member.id = ?1) or b in (select e.brotherhood from Enrolment e where e.member.id = ?1 and e.dropOutMoment is not null)")
	Collection<Brotherhood> findBrotherhoodsNotEnrolledByMember(int memberId);

	// Find Brotherhoods which a member belongs to
	@Query("select b from Brotherhood b where b in (select e.brotherhood from Enrolment e where e.member.id = ?1 and e.dropOutMoment is null)")
	Collection<Brotherhood> findBrotherhoodsEnrolledByMember(int id);

}
