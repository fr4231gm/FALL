
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findCompanyByUserAccountId(int userAccountId);

	@Query("select c from Company c where c.userAccount.username = ?1")
	Company findCompanyByUsername(String username);

}
