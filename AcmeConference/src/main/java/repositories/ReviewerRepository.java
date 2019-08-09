package repositories;
import java.util.Collection;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import domain.Reviewer;
@Repository public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {	@Query("select r from Reviewer r where r.userAccount.id = ?1")	Reviewer findReviewerByUserAccountId(int userAccountId);		@Query("select r from Reviewer r where r.submission.id = ?1")	Collection<Reviewer> findReviewersBySubmission(int submissionId);
}