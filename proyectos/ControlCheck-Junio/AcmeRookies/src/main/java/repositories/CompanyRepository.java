package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	// Find Company by the userAccountId
	@Query("select b from Company b where b.userAccount.id=?1")
	Company findCompanyByUserAccount(int userAccountId);
	
}
