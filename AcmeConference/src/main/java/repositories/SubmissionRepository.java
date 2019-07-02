package repositories;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import domain.Submission;
@Repository public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
	@Query("select s from Submission s where s.paper.id = ?1") 	Submission findSubmissionByPaperId(int paperId);
	@Query("select s from Submission s where s.conference.id = ?1") 	Submission findSubmissionByConferenceId(int conferenceId);
}