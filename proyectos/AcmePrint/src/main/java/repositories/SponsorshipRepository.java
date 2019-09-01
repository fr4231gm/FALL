
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Sponsorship;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//Find sponsorship from ProviderId
	@Query("select s from Sponsorship s where s.provider.id =?1")
	Collection<Sponsorship> findSponsorshipByProviderId(int providerId);

	//Find sponsorship from PostId
	@Query("select s from Sponsorship s where s.post.id =?1")
	Collection<Sponsorship> findSponsorshipByPostId(int postId);
	
	//Find sponsorship from DesignerId
	@Query("select s from Sponsorship s where s.post.designer.id = ?1")
	Collection<Sponsorship> findSponsorshipByDesignerId(int designerId);
	
	//Find sponsorship from PostId
	@Query("select s from Sponsorship s where s.post.id =?1 and s.isEnabled = true")
	Collection<Sponsorship> findEnabledSponsorshipByPostId(int postId);
}
