
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorkPlan;

@Repository
public interface WorkPlanRepository extends JpaRepository<WorkPlan, Integer> {

	@Query("select w from WorkPlan w where w.application.id = ?1")
	WorkPlan findByApplicationId(int applicationId);

}
