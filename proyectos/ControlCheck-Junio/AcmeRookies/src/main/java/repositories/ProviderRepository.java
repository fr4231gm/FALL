package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Provider;

public interface ProviderRepository extends JpaRepository<Provider,Integer> {
	
	//Find Provider by the userAccountId
	@Query("select p from Provider p where p.userAccount.id=?1")
	Provider findProviderByUserAccount(int userAccountId);
	

}
