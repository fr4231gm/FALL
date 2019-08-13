package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/createByConference", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView res;
		Comment comment;
		Conference conference;

		comment = this.commentService.create();
		Assert.notNull(comment);

		conference = this.conferenceService.findOne(conferenceId);

		res = this.createEditModelAndView(comment);
		res.addObject("conference", conference);

		return res;
	}

	@RequestMapping(value = "/createByConference", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, Conference conference, BindingResult binding) {
		ModelAndView res;
		Comment saved;
		
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(comment);
		} else {
			//try {
				saved = this.commentService.save(comment);
				this.conferenceService.addCommentToConference(conference, saved);
				res = new ModelAndView("welcome/index");

			/*} catch (Throwable oops) {
				res = this.createEditModelAndView(comment,
						"comment.commit.error");
			}*/
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

	protected ModelAndView createEditModelAndView(final Comment comment) {
		final ModelAndView result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment,
			final String messagecode) {
		final ModelAndView result;

		result = new ModelAndView("comment/create");

		result.addObject("comment", comment);
		result.addObject("message", messagecode);

		return result;
	}
}
