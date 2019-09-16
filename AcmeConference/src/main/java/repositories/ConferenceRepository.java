
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;
import domain.Submission;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where c.administrator.id = ?1")
	Collection<Conference> findConferencesByAdministratorId(int administratorId);

	@Query("select c from Conference c where c.category.id = ?1")
	Collection<Conference> findConferencesByCategoryId(int categoryId);

	@Query("select c from Conference c where (c.startDate <= ?1 and c.endDate >= ?1) and (c.isDraft = false)")
	Collection<Conference> findRunningConferences(Date actual);

	@Query("select c from Conference c where (c.startDate > ?1) and (c.isDraft = false)")
	Collection<Conference> findForthcomingConferences(Date actual);

	@Query("select c from Conference c where (c.endDate < ?1) and (c.isDraft = false)")
	Collection<Conference> findPastConferences(Date actual);

	@Query("select c from Conference c where (c.startDate <= ?2 and c.endDate >= ?2) and (c.title like %?1% or c.summary like %?1% or c.venue like %?1%) and (c.isDraft = false)")
	Collection<Conference> filterRunning(String keyword, Date actual);

	@Query("select c from Conference c where (c.startDate > ?2) and (c.isDraft = false) and (c.title like %?1% or c.summary like %?1% or c.venue like %?1%)")
	Collection<Conference> filterForthcomming(String keyword, Date actual);

	@Query("select c from Conference c where (c.endDate < ?2) and (c.isDraft = false) and (c.title like %?1% or c.summary like %?1% or c.venue like %?1%)")
	Collection<Conference> filterPast(String keyword, Date actual);

	@Query("select c from Conference c where (c.submissionDeadline < CURRENT_DATE and c.submissionDeadline > ?1)")
	Collection<Conference> findAllDeadlineElapsed(Date x);

	@Query("select c from Conference c where (c.notificationDeadline < CURRENT_DATE and c.notificationDeadline > ?1)")
	Collection<Conference> findAllNotificationElapsed(Date x);

	@Query("select c from Conference c where (c.cameraReadyDeadline < CURRENT_DATE and c.cameraReadyDeadline > ?1)")
	Collection<Conference> findAllCameraElapsed(Date x);

	
	@Query("select c from Conference c where (c.startDate > CURRENT_DATE and c.startDate < ?1)")
	Collection<Conference> findAllFutureConferences(Date x);


	@Query("select s from Submission s where s.conference.id =?1 and s.status='ACCEPTED' and s.paper.cameraReadyPaper = true")
	Collection<Submission> findSubmissionsPapersAcceptedByConferenceId(int conferenceId);
	
	@Query("select c from Conference c where c.isDraft = 'false'") 
	Collection<Conference> findAllNoDraft();
	
}
