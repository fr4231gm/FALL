
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	//Find problems by CompanyId
	@Query("select p from Problem p where p.company.id=?1")
	Collection<Problem> findProblemsByCompanyId(int companyId);
}
