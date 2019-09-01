
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Post;

@Service
@Transactional
public class CommentService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services -----------------------------------------------------

	@Autowired
	private PostService			postService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods -----------------------------------------------------
	public Comment create(final int postId) {
		Assert.notNull(postId);
		Comment res;
		Actor principal;
		Post post;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		post = this.postService.findOne(postId);
		Assert.isTrue(post.getIsDraft() == false);

		res = new Comment();
		res.setPost(post);

		return res;
	}
	public Comment save(final Comment comment) {
		Assert.isTrue(comment.getId() == 0);
		Comment res;
		Collection<Comment> comments = new ArrayList<>();
		comments = this.findCommentsByPostId(comment.getPost().getId());
		Double score = 0.0;
		final Post p = this.postService.findOne(comment.getPost().getId());

		if (comment.getType().equals("PRINTING EXPERIENCE")) {

			Assert.isTrue(comment.getPictures() != "" && comment.getPictures() != null);
			Assert.isTrue(comment.getScore() != null);
			comments.add(comment);
			if (!comments.isEmpty()) {
				Integer taman = comments.size();
				for (final Comment c : comments)
					if (c.getScore() != null)
						score = score + c.getScore();
					else
						taman = taman - 1;
				score = score / taman;
			} else
				score = -1.0;

			if (score != 0 && score > 0)
				p.setScore(score);
			else if (score == 0.0)
				p.setScore(score);

		}
		Assert.isTrue(comment.getPost().getIsDraft() == false);
		res = this.commentRepository.save(comment);

		return res;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;

	}

	public Comment findOne(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);

		Assert.notNull(result);
		return result;
	}

	// Other business methods --------------------------------------------------

	public Collection<Comment> findCommentsByDesignerId(final int designerId) {
		Collection<Comment> res;

		res = this.commentRepository.findCommentsByDesignerId(designerId);

		return res;
	}

	public Collection<Comment> findCommentsByPostId(final int postId) {
		Collection<Comment> res;

		res = this.commentRepository.findCommentsByPostId(postId);

		return res;
	}

	// Return true if at least one of the pictures is not an URL
	public boolean checkPictures(final String pictures) {
		boolean res = false;
		final String[] aux = pictures.split(", ");
		for (int i = 0; i < aux.length; i++)
			if (!(aux[i].matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) && aux[i].length() > 0)
				res = true;
		return res;
	}
}
