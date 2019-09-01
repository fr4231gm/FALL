
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	// Find Post by its Ticker
	@Query("select p from Post p where p.ticker = ?1")
	Post findPostByTicker(String ticker);

	// Find Posts by DesignerId
	@Query("select p from Post p where p.designer.id = ?1")
	Collection<Post> findPostsByDesignerId(int designerId);

	// Find Posts in final mode by DesignerId
	@Query("select p from Post p where p.isDraft = false and p.designer.id = ?1")
	Collection<Post> findPostsFinalModeByDesignerId(int designerId);

	// Find Posts in final mode
	@Query("select p from Post p where p.isDraft = false")
	Collection<Post> findPostsFinalMode();

	// Find Posts in final mode
	@Query("select p from Post p where p.designer.id = ?1")
	Collection<Post> findAllByDesigner(int designerId);

	@Query("select p from Post p where (p.isDraft = false) and (p.ticker like %?1% or p.description like %?1% or p.category like %?1% or p.title like %?1%)")
	Collection<Post> filter(String keyword);

}
