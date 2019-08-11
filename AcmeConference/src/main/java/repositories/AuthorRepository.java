
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	// Find Author by the userAccountId
	@Query("select b from Author b where b.userAccount.id=?1")
	Author findAuthorByUserAccount(int userAccountId);

	// Find Author whith submissions that have a camera ready paper
	@Query("select distinct s.author from Submission s where s.paper.cameraReadyPaper = true")
	Collection<Author> findAuthorsWithCameraReadyPapers();
}