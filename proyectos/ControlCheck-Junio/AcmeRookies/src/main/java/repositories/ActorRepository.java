package repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findActorByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.userAccount.username = ?1")
	Actor findActorByUsername(String username);

	// Check if an actor can be considered as spammer
	@Query("select m.isSpam from Message m where m.isSpam = true and m.sender.id=?1 group by m.sender.id having count(m) > ( select 0.1*count(me) from Message me where me.isSpam=true )")
	Collection<Boolean> checkActorHasSpamMessages(int actorId);

}
