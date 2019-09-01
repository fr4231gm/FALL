
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	// Find Chapter by the userAccountId
	@Query("select b from Chapter b where b.userAccount.id=?1")
	Chapter findChapterByUserAccount(int userAccountId);

}
