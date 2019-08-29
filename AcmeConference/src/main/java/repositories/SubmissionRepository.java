package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Paper;
import domain.Submission;

@Repository
public interface SubmissionRepository extends
		JpaRepository<Submission, Integer> {

	@Query("select s from Submission s where s.conference.id = ?1")
	Submission findSubmissionByConferenceId(int conferenceId);

	@Query("select s from Submission s where s.paper.title=:texto")
	Submission findSubmissionByPaperTitle(@Param("texto") String texto);

	@Query("select s from Submission s where s.author.id = ?1")
	Collection<Submission> findByAuthorId(int id);
	
	@Query("select s.paper from Submission s where s.author.id = ?1 and s.paper.cameraReadyPaper = true")
	Collection<Paper> findCameraReadyPapersByAuthorId(int authorId);

	@Query("select distinct s from Reviewer r join r.submissions s where r.id = ?1 and s.id not in (select x.submission.id from Report x where x.reviewer.id = ?1)")
	Collection<Submission> findReportablesSubmissions(int reviewerId);

}
