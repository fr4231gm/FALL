package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.CommentRepository;
import domain.Comment;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CommentRepository commentRepository;
	

	// Supporting services ----------------------------------------------------

	// @Autowired
	// private ActorService actorService;

	 @Autowired
	 private ConferenceService conferenceService;
	
	 @Autowired
	 private ActivityService activityService;
	
	// Constructors ------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUDs methods ---------------------------------------------------
	public Comment save(Comment comment) {
		Comment res;
		comment.setMoment(new Date(System.currentTimeMillis()-1));
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
	
	public Comment reconstruct(CommentForm commentForm,  final BindingResult binding){
		Comment res = new Comment();
		
		res.setId(commentForm.getId());
		res.setVersion(commentForm.getVersion());
		
		res.setAuthor(commentForm.getAuthor());
		res.setText(commentForm.getText());
		res.setTitle(commentForm.getTitle());
		
		if((commentForm.getConference() != null && commentForm.getActivity() != null) || (commentForm.getConference() == null && commentForm.getActivity() == null)){
			binding.rejectValue("title", "comment.duplicated.target");
		}
		return res;
	}
	
	public Object findConferenceOrActivity(int objectId){
		Object res;
		 res = this.conferenceService.findOne(objectId);
		 if (res == null){
			 res = this.activityService.findOne(objectId);
		 }
		return res;

	}

}
