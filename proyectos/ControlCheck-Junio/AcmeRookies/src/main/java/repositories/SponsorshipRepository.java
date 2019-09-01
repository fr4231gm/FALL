
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Sponsorship;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//Find sponsorship from ProviderId
	@Query("select s from Sponsorship s where s.provider.id =?1")
	Collection<Sponsorship> findSponsorshipByProviderId(int providerId);

	//Find sponsorship from PositionId
	@Query("select s from Sponsorship s where s.position.id =?1")
	Collection<Sponsorship> findSponsorshipByPositionId(int positionId);

	@Query("select s from Sponsorship s where s.position.id =?1 and s.isEnabled = 'true'")
	Collection<Sponsorship> findEnabledSponsorshipByPositionId(int positionId);

}
