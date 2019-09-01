package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.member.id = ?1")
	Collection<Request> findByMember(int memberId);
	
	@Query("select r from Request r where r.procession.brotherhood.id = ?1")
	Collection<Request> findByBrotherhood(int brotherhoodId);
	
	@Query("select r from Request r where r.procession.id = ?1")
	Collection<Request> findByProcession(final int processionId);
	
}

