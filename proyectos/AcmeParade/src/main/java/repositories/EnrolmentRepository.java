package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enrolment;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	@Query("select e from Enrolment e where e.member.id=?1")
	Collection<Enrolment> findByMember(int memberId);
	
	@Query("select e from Enrolment e where e.brotherhood.id=?1 and e.dropOutMoment is null")
	Collection<Enrolment> findByBrotherhood(int brotherhoodId);

	@Query("select e from Enrolment e where e.member.id=?1 and e.brotherhood.id=?2")
	Enrolment findByActorAndBrotherhood(int memberId, int brotherhoodId);
	
	@Query("select e from Enrolment e where e.brotherhood.id=?1 and e.dropOutMoment is null and e.position is null")
	Collection<Enrolment> findEnrolmentsWithoutPositionByBrotherhood(int brotherhoodId);
	
	@Query("select e from Enrolment e where e.brotherhood.id=?1 and e.dropOutMoment is null and e.position is not null")
	Collection<Enrolment> findEnrolmentsWithPositionByBrotherhood(int brotherhoodId);

	@Query("select e from Enrolment e where e.position.id=?1")
	Collection<Enrolment> findByPositionId(int positionId);
	
}

