
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Author;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findActorByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.userAccount.username = ?1")
	Actor findActorByUsername(String username);
	
	@Query("select a from Actor a where type(a) != Administrator")
	Collection<Actor> findAllButAdmins();
	
	@Query("select s.author from Submission s where s.conference.id = ?1")
	Collection<Actor> findAuthorsSubmittedByConferenceId(int conferenceId);
	
	@Query("select r.author from Registration r where r.conference.id = ?1")
	Collection<Actor> findAuthorsRegisteredByConferenceId(int conferenceId);
}
