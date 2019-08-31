package repositories;
import java.util.Collection; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.data.jpa.repository.Query; import org.springframework.stereotype.Repository; import domain.Quolet;
@Repository public interface QuoletRepository extends JpaRepository<Quolet, Integer> {
	@Query("select q from Quolet q where q.administrator.id = ?1") 	Collection<Quolet> findQuoletsByAdministratorId(int administratorId);
	@Query("select q from Quolet q where q.conference.id = ?1") 	Collection<Quolet> findQuoletsByConferenceId(int conferenceId);
}