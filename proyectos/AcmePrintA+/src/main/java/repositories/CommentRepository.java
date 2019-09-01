
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.post.designer.id = ?1")
	Collection<Comment> findCommentsByDesignerId(int designerId);

	@Query("select c from Comment c where c.post.id = ?1")
	Collection<Comment> findCommentsByPostId(int postId);

}
