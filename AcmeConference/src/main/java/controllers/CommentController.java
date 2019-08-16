package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.CommentService;
import services.ConferenceService;
import domain.Activity;
import domain.Comment;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Tutorial;
import forms.CommentForm;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int targetId) {
		ModelAndView res;
		CommentForm commentForm = new CommentForm();
		try{
			Object target = this.commentService.findConferenceOrActivity(targetId);
			if(target.getClass() == Conference.class){
				commentForm.setConference((Conference) target);
			}
			else if(target.getClass() == Activity.class){
				commentForm.setActivity((Activity) target);
			}
			res = this.createEditModelAndView(commentForm);
		} catch (Throwable oops) {
			res = new ModelAndView("welcome/index");
			res.addObject("message", "comment.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CommentForm commentForm, BindingResult binding) {
		ModelAndView res = null;
		Comment saved;
		Comment comment = this.commentService.reconstruct(commentForm, binding);
		
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(commentForm);
		} else {
			try {
				saved = this.commentService.save(comment);
				
				if(commentForm.getConference() != null){
					this.conferenceService.addCommentToConference(commentForm.getConference(), saved);
					res = new ModelAndView("redirect:/conference/display.do?conferenceId=" + commentForm.getConference().getId());
				} else {
					Activity activity = commentForm.getActivity();
					this.activityService.addCommentToActivity(activity, saved);
					if(activity instanceof Panel){
						res = new ModelAndView("redirect:/panel/display.do?panelId=" + commentForm.getConference().getId());
					}
					if(activity instanceof Tutorial){
						res = new ModelAndView("redirect:/tutorial/display.do?tutorialId=" + commentForm.getConference().getId());
					}
					if(activity instanceof Presentation){
						res = new ModelAndView("redirect:/presentation/display.do?presentationId=" + commentForm.getConference().getId());
					}
					
				}

			} catch (Throwable oops) {
				res = this.createEditModelAndView(commentForm, "comment.commit.error");
			}
		}

		return res;
	}

	@RequestMapping(value = "/listByConference", method = RequestMethod.GET)
	public ModelAndView listByConference(@RequestParam int conferenceId) {
		ModelAndView res;
		Conference conference;
		Collection<Comment> comments;

		conference = this.conferenceService.findOne(conferenceId);
		comments = conference.getComments();

		res = new ModelAndView("comment/list");
		res.addObject("comments", comments);

		return res;
	}

	@RequestMapping(value = "/listByActivity", method = RequestMethod.GET)
	public ModelAndView listByActivity(@RequestParam int activityId) {
		ModelAndView res;
		Activity activity;
		Collection<Comment> comments;

		activity = this.activityService.findOne(activityId);
		comments = activity.getComments();

		res = new ModelAndView("comment/list");
		res.addObject("comments", comments);

		return res;
	}

	// Ancilliary methods
	// --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CommentForm commentForm) {
		final ModelAndView result = this.createEditModelAndView(commentForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CommentForm commentForm,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("comment/create");

		result.addObject("commentForm", commentForm);
		result.addObject("message", messagecode);

		return result;
	}
}
