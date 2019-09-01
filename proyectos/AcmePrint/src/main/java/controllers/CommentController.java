
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

import services.ActorService;
import services.CommentService;
import domain.Actor;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int postId) {
		ModelAndView res;
		final Collection<Comment> comments;

		comments = this.commentService.findCommentsByPostId(postId);

		res = new ModelAndView("comment/list");
		res.addObject("comments", comments);
		res.addObject("requestURI", "/comment/list.do?postId=" + postId);

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		String picturesList;

		comment = this.commentService.findOne(commentId);

		picturesList = comment.getPictures();

		final String[] aux = picturesList.split(", ");

		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("picturesList", aux);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int postId) {
		ModelAndView res;
		Actor principal;
		Comment comment;

		principal = this.actorService.findByPrincipal();

		if (principal == null)
			res = new ModelAndView("security/hacking");
		else {
			comment = this.commentService.create(postId);
			res = this.createEditModelAndView(comment);
		}
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView res;
		Comment aux;

		if (this.commentService.checkPictures(comment.getPictures()))
			binding.rejectValue("pictures", "comment.pictures.url.error");
		if (comment.getType().equals("PRINTING EXPERIENCE") && (comment.getPictures() == null || comment.getPictures() == ""))
			binding.rejectValue("pictures", "comment.pictures.error");
		if (binding.hasErrors())
			res = this.createEditModelAndView(comment);
		else
			try {
				aux = this.commentService.save(comment);
				res = new ModelAndView("redirect:display.do?commentId=" + aux.getId());
				res.addObject("comment", aux);

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return res;
	}
	// Ancillary metods

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;
		result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("message", messageCode);

		return result;
	}
}
