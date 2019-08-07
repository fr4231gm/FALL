package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository commentRepository;

	// Supporting services ----------------------------------------------------

	//@Autowired
	//private ActorService actorService;

	// @Autowired
	// private ConferenceService conferenceService;
	//
	// @Autowired
	// private ActivityService activityService;

	// Constructors ------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUDs methods ---------------------------------------------------

	public Comment create() {
		Comment c;

		c = new Comment();
		c.setMoment(new Date(System.currentTimeMillis() - 1));

		return c;
	}

	public Comment save(Comment comment) {
		Comment res;

		res = this.commentRepository.save(comment);

		return res;
	}

	public Comment findOne(final int commentId) {

		final Comment reg = this.commentRepository.findOne(commentId);

		return reg;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;

	}

}
