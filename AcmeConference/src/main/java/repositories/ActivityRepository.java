package repositories;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import domain.Activity;
@Repository public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	@Query("select a from Activity a where a.conference.id = ?1") 	Activity findActivityByConferenceId(int conferenceId);
}